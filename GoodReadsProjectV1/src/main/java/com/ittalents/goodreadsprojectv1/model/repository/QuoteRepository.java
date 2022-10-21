package com.ittalents.goodreadsprojectv1.model.repository;

import com.ittalents.goodreadsprojectv1.model.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote,Long> {

    public Quote findById(long id);

    //find by author-book-quote (FK)
    //find by book title(FK)
    //find by author and book title
    //search by key words**
}
