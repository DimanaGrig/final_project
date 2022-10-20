package com.ittalents.goodreadsprojectv1.controller;


import com.ittalents.goodreadsprojectv1.model.dto.ErrorDTO;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


public abstract class AbstractController {


    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorDTO handlerNotFound(Exception e) {
        return buildInfo(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDTO handlerBadRequest(Exception e) {
        //        хващаме и двата варианта аз проверка на имейл - не е правилен/има го в базата :)
        return buildInfo(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ErrorDTO handlerUnauthorized(Exception e) {
        return buildInfo(e, HttpStatus.UNAUTHORIZED);
    }

    private ErrorDTO buildInfo(Exception e, HttpStatus status) {
        e.printStackTrace();//add to log file
        ErrorDTO dto = new ErrorDTO();
        dto.setMassage(e.getMessage());
        dto.setStatus(status.value());
        dto.setTime(LocalDateTime.now());
        return dto;
    }

}
