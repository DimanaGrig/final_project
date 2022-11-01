package com.ittalents.goodreadsprojectv1.model.repository;

import com.ittalents.goodreadsprojectv1.model.entity.Author;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long>{

    public Optional<Book> findByIsbn(long isbn);
    public boolean existsByIsbn(long isbn);
    public void deleteByIsbn(long isbn);
    public List<Book>  findByNameContainingIgnoreCase(String keyword);
}
