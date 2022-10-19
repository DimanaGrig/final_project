package com.ittalents.goodreadsprojectv1.model.exceptions;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String msg){
        super(msg);
    }
}
