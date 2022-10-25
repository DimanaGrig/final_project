package com.ittalents.goodreadsprojectv1.model.dto.users;

import com.ittalents.goodreadsprojectv1.model.dto.comments.CommentWithoutRelationsDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserCommentsDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String photo;
    private List<CommentWithoutRelationsDTO> userCommentsForReviews;
}
