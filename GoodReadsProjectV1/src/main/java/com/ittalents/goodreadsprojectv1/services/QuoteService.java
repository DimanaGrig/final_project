package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.dao.QuoteDBDAO;
import com.ittalents.goodreadsprojectv1.model.repository.QuoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {
    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private QuoteDBDAO quoteDBDAO;
}
