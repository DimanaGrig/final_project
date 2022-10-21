package com.ittalents.goodreadsprojectv1.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository  extends JpaRepository {

    //find by id
    //find by author
    //find by book title
    //find by author and book title
}
