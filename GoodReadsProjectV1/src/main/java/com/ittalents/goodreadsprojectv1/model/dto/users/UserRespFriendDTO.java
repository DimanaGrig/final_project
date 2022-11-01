package com.ittalents.goodreadsprojectv1.model.dto.users;

import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewRespFriendDTO;
import lombok.Data;

@Data
public class UserRespFriendDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String photo;
    private String address;
    private ReviewRespFriendDTO highestRateReview;
    private int totalBooks;
    private int totalFriends;
}
