package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class BookWithoutQuotesDTO {
    private long isbn;
    private String name;
    private String cover;
    private String content;
    private String additionalInfo;
    private List<GenreWithoutBooksDTO> genres;
    private AuthorWithoutBooksDTO author;
}
