package com.ittalents.goodreadsprojectv1.model.repository;

import com.ittalents.goodreadsprojectv1.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {


    public List<User> findAllByEmail(String email);

    public Optional<User> findByEmail(String email);

    public Optional<User> findById(Integer id);

    public List<User> findAll();

    public void deleteById(int id);

    public List<User> findAllByFirstNameLike(String name);


}
