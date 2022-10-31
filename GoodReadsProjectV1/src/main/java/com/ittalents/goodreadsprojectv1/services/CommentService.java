package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.dto.comments.CommentChangeDTO;
import com.ittalents.goodreadsprojectv1.model.dto.comments.CommentDTO;
import com.ittalents.goodreadsprojectv1.model.dto.comments.CommentReqCreateDTO;
import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Comment;
import com.ittalents.goodreadsprojectv1.model.entity.Review;
import com.ittalents.goodreadsprojectv1.model.entity.User;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService extends AbstractService {


    public CommentDTO createNewComment(CommentReqCreateDTO dto, int id) {
            Comment comment = new Comment();
            Review review = getReviewById(dto.getReviewId());
            User user = getUserById(id);
            comment.setComment(dto.getComment());
            comment.setCommentReview(review);
            comment.setCommentedAt(LocalDateTime.now());
            comment.setUser(user);
            commentRepository.save(comment);
            return modelMapper.map(comment, CommentDTO.class);
    }

    public void deleteComment(int cid, int id) {
        Comment comment  = getCommentById(cid);
        if (comment.getUser().getId() == id) {
            commentRepository.deleteById(cid);
            System.out.println("Comment with id " + id + "have been deleted.");
        } else {
            throw new UnauthorizedException("You can't delete this review!");
        }
    }
    public CommentDTO editComment(CommentChangeDTO dto, int id) {
        Comment comment = getCommentById(dto.getId());
        if (comment.getUser().getId() != id) {
            throw new UnauthorizedException("This comment is not yours to change it!");
        }
        comment.setComment(dto.getNewComment());
        comment.setCommentedAt(LocalDateTime.now());
        commentRepository.save(comment);
        return modelMapper.map(comment, CommentDTO.class);
    }

    public CommentDTO geById(int cid) {
        Comment comment = getCommentById(cid);
        CommentDTO dto = modelMapper.map(comment, CommentDTO.class);
        UserWithoutRelationsDTO user = modelMapper.map(comment.getUser(), UserWithoutRelationsDTO.class);
        dto.setUser(user);
        dto.setCommentReview(modelMapper.map(comment.getCommentReview(), ReviewWithoutRelationsDTO.class));
        return dto;
    }


}
