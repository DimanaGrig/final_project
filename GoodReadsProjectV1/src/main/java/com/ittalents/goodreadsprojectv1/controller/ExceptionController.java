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
        ErrorDTO dto = new ErrorDTO();
        dto.setMassage("sorry , but not this what you are looking for is not existing " + e.getMessage());
        dto.setStatus(HttpStatus.NOT_FOUND.value());
        dto.setTime(LocalDateTime.now());
        return dto;
    }
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDTO handlerBadRequest(Exception e){
        ErrorDTO dto = new ErrorDTO();
//        хващаме и двата варианта аз проверка на имейл - не е правилен/има го в базата :)
        dto.setMassage("sorry , but this email is not suitable for registration" + e.getMessage());
        dto.setStatus(HttpStatus.BAD_REQUEST.value());
        dto.setTime(LocalDateTime.now());
        return dto;
    }

}
