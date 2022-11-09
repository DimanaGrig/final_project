package com.ittalents.goodreadsprojectv1.model.dto.users;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookFriendDTO;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Author;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.model.entity.Review;
import lombok.Data;

@Data
public class UserFriendDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String photo;
    private ReviewWithoutRelationsDTO review;
    private BookFriendDTO book;
    private AuthorWithoutRelationsDTO author;
}
