package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.entity.*;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ShelfRepository shelfRepository;
    @Autowired
    protected GenreRepository genreRepository;
    @Autowired
    protected BookRepository bookRepository;
    @Autowired
    protected ReviewRepository reviewRepository;
    @Autowired
    protected CommentRepository commentRepository;
    @Autowired
    protected ModelMapper modelMapper;


    protected User getUserById(int uid) {
        return userRepository.findById(uid).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    protected Genre getGenreById(int id) {
        return genreRepository.findById(id).orElseThrow(() -> new NotFoundException("Genre not found!"));
    }

    protected Shelf getShelfById(int id) {
        return shelfRepository.findById(id).orElseThrow(() -> new NotFoundException("Shelf not found!"));
    }

    protected Book getBookByISBN(long isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            return bookRepository.findByIsbn(isbn).orElseThrow(() -> new NotFoundException("Shelf not found!"));
        }
    return null;
    }
    protected  Shelf getShelfByName(String name){
        return shelfRepository.getShelfByName(name).orElseThrow(()->new NotFoundException("Shelf not exist!"));

    }
    protected Review getReviewById(int id){
        return reviewRepository.getReviewById(id).orElseThrow(()-> new NotFoundException("This review not exist."));
    }

    protected Comment getCommentById(int id){
        return commentRepository.getCommentById(id).orElseThrow(()-> new NotFoundException("This comment doesn't exist."));
    }

    protected  User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found!"));
    }


}

