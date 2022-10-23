package com.ittalents.goodreadsprojectv1.model.dto.users;

import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewWithoutRelationsDTO;
import lombok.Data;

import java.util.List;

@Data

public class UsersReviewsDTO {

    private int id;
    private String firstName;
    private String lastNme;
    private String photo;
    private List<ReviewWithoutRelationsDTO> writtenReviews;
}
