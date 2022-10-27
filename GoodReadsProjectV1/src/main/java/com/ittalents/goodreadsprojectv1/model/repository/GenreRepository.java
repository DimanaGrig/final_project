package com.ittalents.goodreadsprojectv1.model.repository;


import com.ittalents.goodreadsprojectv1.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

    public Optional<Genre> findById(int id);
    public Optional<Genre> findByName(String Name);


}
