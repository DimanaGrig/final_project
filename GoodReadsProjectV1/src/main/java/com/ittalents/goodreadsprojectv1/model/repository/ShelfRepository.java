package com.ittalents.goodreadsprojectv1.model.repository;

import com.ittalents.goodreadsprojectv1.model.entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShelfRepository extends JpaRepository<Shelf,Integer> {

    public Optional<Shelf> findById(Integer id);

    public void deleteById(Integer id);
public boolean existsById(Integer id);

}
