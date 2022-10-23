package com.ittalents.goodreadsprojectv1.model.dto.users;

import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewWithoutRelationsDTO;
import lombok.Data;

import java.util.Set;

@Data
public class UsersLikedReviews {

    private int id;
    private String firstName;
    private String lastNme;
    private String photo;
    Set<ReviewWithoutRelationsDTO> likedReviews;
}
