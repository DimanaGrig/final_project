package com.ittalents.goodreadsprojectv1.controller;


import com.ittalents.goodreadsprojectv1.model.dto.ErrorDTO;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public  abstract class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code =HttpStatus.NOT_FOUND)
    public ErrorDTO handlerNotFound(Exception e) {
        return buildErrorInfo(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDTO handlerBadRequest(Exception e){
        return buildErrorInfo(e,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code =HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleUnauthorizedException(Exception e) {
        return buildErrorInfo(e,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code =HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleAllOthers(Exception e) {
        return buildErrorInfo(e,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDTO buildErrorInfo(Exception e, HttpStatus status){
        ErrorDTO dto=new ErrorDTO();
        dto.setStatus(status.value());
        dto.setMassage(e.getMessage());
        dto.setTime(LocalDateTime.now());
        return dto;
    }
}
