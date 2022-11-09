package com.ittalents.goodreadsprojectv1.model.dto.users;

import com.ittalents.goodreadsprojectv1.model.entity.Author;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.model.entity.Review;
import lombok.Data;

@Data
public class UserHelpDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String photo;
    private Review review;
    private Book book;
    private Author author;

}
