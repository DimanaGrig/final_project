package com.ittalents.goodreadsprojectv1.model.dto.comments;

import com.ittalents.goodreadsprojectv1.model.entity.Review;
import lombok.Data;

import javax.persistence.Column;

@Data
public class CommentReqCreateDTO {
    private int reviewId;
    private String comment;
}
