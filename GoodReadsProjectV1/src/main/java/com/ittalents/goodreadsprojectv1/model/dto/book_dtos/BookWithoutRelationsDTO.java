package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutRelationsDTO;
import lombok.Data;

@Data
public class BookWithoutRelationsDTO {
//    za shelf
    private long id;
    private String name;
    private String bookCover;
    private AuthorWithoutRelationsDTO author;

//    get rate from reviews!
}
