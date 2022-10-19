package com.ittalents.goodreadsprojectv1.model.exceptions;

public class NotFoundException extends  RuntimeException{
    public NotFoundException(String msg){
        super(msg);
    }
}
