package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper modelMapper;
    //!Validate here

    //Optional<Author> author=authorRepository.findAuthorByFirstNameOrderByLastName("asd","ad");
    //!!!return object mapped to the dto with the to-do-work
    //if - is present()
//modelMappper.map(user.get() /bc we work with optional/, UserWithoutPassDTO.class)
    //->else throw exception
}
