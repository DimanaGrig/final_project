package com.ittalents.goodreadsprojectv1.model.dto.users;

import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewWithoutRelationsDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserLikedReviews {

    private int id;
    private String firstName;
    private String lastName;
    private String photo;
    private List<ReviewWithoutRelationsDTO> likedReviews;
}
