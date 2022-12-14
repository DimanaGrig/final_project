package com.ittalents.goodreadsprojectv1.model.dto.users;

import com.ittalents.goodreadsprojectv1.model.dto.comments.CommentWithoutRelationsDTO;

import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfWithoutRelationsDTO;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class UserRespWithoutPassDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private String website;
    private String infoForUser;
    private LocalDateTime memberFrom;
    private LocalDateTime lastEnter;
    private String address;
    private String photo;
    private List<GenreWithoutBooksDTO> likedGenres;
    private List<ReviewWithoutRelationsDTO> writtenReviews;
    private List<ReviewWithoutRelationsDTO> likedReviews;
    private List<CommentWithoutRelationsDTO> userCommentsForReviews;
    private List<ShelfWithoutRelationsDTO> userShelves;
    private List<UserWithoutRelationsDTO> followers;
    private List<UserWithoutRelationsDTO> following;


}
