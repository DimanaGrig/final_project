package com.ittalents.goodreadsprojectv1.model.repository;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long>{

    public Optional<Book> findByIsbn(long isbn);
    public boolean existsByIsbn(long isbn);
    public void deleteByIsbn(long isbn);
}
