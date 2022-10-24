package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import lombok.Data;

@Data
public class ShowBookDTO {
    private long isbn;
    private String name;
    private String content;
    private String cover;
    private String additionalInfo;
    private AuthorWithoutBooksDTO author;
}
