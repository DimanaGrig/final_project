package com.ittalents.goodreadsprojectv1.model.repository;

import com.ittalents.goodreadsprojectv1.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    public Author findAuthorById(long aid);
    public List<Author> findAuthorByFirstNameOrderByLastName(String firstName);
    public List<Author> findAuthorByLastNameOrderByFirstName(String lastName);
    public List<Author> findAuthorByFirstNameAndLastNameOrderByFirstName(String firstName, String lastName);

}
