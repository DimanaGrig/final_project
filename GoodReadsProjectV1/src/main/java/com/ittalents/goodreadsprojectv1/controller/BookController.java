package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.repository.BookRepository;
import com.ittalents.goodreadsprojectv1.model.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController extends ExceptionController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ModelMapper modelMapper;

    //Functionality - rate, review, upload

}
