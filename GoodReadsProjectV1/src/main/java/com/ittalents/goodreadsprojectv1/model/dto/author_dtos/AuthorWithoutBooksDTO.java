package com.ittalents.goodreadsprojectv1.model.dto.author_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookDTO;
import lombok.Data;

@Data
public class AuthorWithoutBooksDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String picture;
    private String informationForAuthor;
    private String authorWebsite;
}
