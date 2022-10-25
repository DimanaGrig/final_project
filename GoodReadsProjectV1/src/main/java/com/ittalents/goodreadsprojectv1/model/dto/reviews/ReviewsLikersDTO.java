package com.ittalents.goodreadsprojectv1.model.dto.reviews;

import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ReviewsLikersDTO {

    private int id;
    private int rate;
    private String opinion;
    private List<UserWithoutRelationsDTO> usersLikedThisReview;
}
