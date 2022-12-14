package com.ittalents.goodreadsprojectv1.model.dto.author_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookDTO;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookWithoutAuthorDTO;
import lombok.Data;

import java.util.List;

@Data
public class AuthorDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String picture;
    private String informationForAuthor;
    private String authorWebsite;
    private List<BookWithoutAuthorDTO> books;
}
