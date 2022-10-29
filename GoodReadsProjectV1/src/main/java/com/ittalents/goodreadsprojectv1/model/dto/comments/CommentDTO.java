package com.ittalents.goodreadsprojectv1.model.dto.comments;

import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Review;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private int id;
    private UserWithoutRelationsDTO user;
    private ReviewWithoutRelationsDTO commentReview;
    private String comment;
    private LocalDateTime commentedAt;
}
