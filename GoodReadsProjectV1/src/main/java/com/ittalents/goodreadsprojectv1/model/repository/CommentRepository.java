package com.ittalents.goodreadsprojectv1.model.repository;

import com.ittalents.goodreadsprojectv1.model.entity.Comment;
import com.ittalents.goodreadsprojectv1.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    public Optional<Comment> getCommentById(int id);
}
