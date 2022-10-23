package com.ittalents.goodreadsprojectv1.controller;


import com.ittalents.goodreadsprojectv1.services.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController extends AbstractController {
    @Autowired
    private AuthorService authorService;

}
