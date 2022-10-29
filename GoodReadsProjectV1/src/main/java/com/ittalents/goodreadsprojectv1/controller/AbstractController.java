package com.ittalents.goodreadsprojectv1.controller;


import com.ittalents.goodreadsprojectv1.model.dto.ErrorDTO;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;


public abstract class AbstractController {
    @Autowired
    protected ModelMapper modelMapper;

    public static final String LOGGED = "LOGGED";
    public static final String USER_ID = "USER_ID";
    public static final String REMOTE_IP = "REMOTE_IP";

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorDTO handlerNotFound(Exception e) {
        return buildErrorInfo(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDTO handlerBadRequest(Exception e) {
        return buildErrorInfo(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleUnauthorizedException(Exception e) {
        return buildErrorInfo(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleAllOthers(Exception e) {
        return buildErrorInfo(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDTO buildErrorInfo(Exception e, HttpStatus status) {
        e.printStackTrace(); //add to log file
        ErrorDTO dto = new ErrorDTO();
        dto.setStatus(status.value());
        dto.setMassage(e.getMessage());
        dto.setTime(LocalDateTime.now());
        return dto;
    }

    public int getLoggedUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String ip = request.getRemoteAddr();
        if (session.isNew() ||
                session.getAttribute(LOGGED) == null ||
                !(boolean) session.getAttribute(LOGGED) ||
                !session.getAttribute(REMOTE_IP).equals(ip)) {
            return -1;
        }
        return (int) session.getAttribute(USER_ID);
    }

    public void logUser(HttpServletRequest request, int id) {
        HttpSession s = request.getSession();
        s.setAttribute(LOGGED, true);
        s.setAttribute(USER_ID, id);
        s.setAttribute(REMOTE_IP, request.getRemoteAddr());
    }

    public boolean checkLog(int id) {
        if (id < 0) {
            throw new UnauthorizedException("You have to login!");
        }
        return true;
    }



}
