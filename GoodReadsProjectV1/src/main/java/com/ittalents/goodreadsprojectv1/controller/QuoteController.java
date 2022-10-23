package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.repository.BookRepository;
import com.ittalents.goodreadsprojectv1.services.AuthorService;
import com.ittalents.goodreadsprojectv1.services.QuoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController extends AbstractController {
    @Autowired
    private QuoteService quoteService;

}
