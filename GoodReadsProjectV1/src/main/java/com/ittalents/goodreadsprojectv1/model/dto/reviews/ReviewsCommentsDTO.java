package com.ittalents.goodreadsprojectv1.model.dto.reviews;

import com.ittalents.goodreadsprojectv1.model.dto.comments.CommentWithoutRelationsDTO;
import lombok.Data;

import java.util.Set;

@Data
public class ReviewsCommentsDTO {

    private int id;
    private int rate;
    private String opinion;
    private Set<CommentWithoutRelationsDTO> commentsForThisReview;
}
