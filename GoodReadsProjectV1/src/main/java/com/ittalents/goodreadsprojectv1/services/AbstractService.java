package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.entity.Shelf;
import com.ittalents.goodreadsprojectv1.model.entity.User;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.repository.ShelfRepository;
import com.ittalents.goodreadsprojectv1.model.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ShelfRepository shelfRepository;

    @Autowired
    protected ModelMapper modelMapper;


    protected User getUserById(int uid){
        return userRepository.findById((long) uid).orElseThrow(()-> new NotFoundException("User not found!"));
    }
}
