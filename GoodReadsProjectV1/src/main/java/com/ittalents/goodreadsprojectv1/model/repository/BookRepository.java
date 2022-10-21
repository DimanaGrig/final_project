package com.ittalents.goodreadsprojectv1.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository {

    //get by isbn
    //get by title
    //get by title and author
    //get by author (FK)
    //get by language (FK)
    //get by genre (FK)
    //get all other editions for same book (diff IBSN)

}
