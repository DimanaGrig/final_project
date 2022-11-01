package com.ittalents.goodreadsprojectv1.model.repository;

import com.ittalents.goodreadsprojectv1.model.entity.Author;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    public Optional<Author> findAuthorById(int aid);
    public boolean existsAuthorById(int aid);
    public void deleteById(int aid);
    public List<Author>  findByFirstNameContainingIgnoreCase(String keyword);
    public List<Author>  findByLastNameContainingIgnoreCase(String keyword);
}
