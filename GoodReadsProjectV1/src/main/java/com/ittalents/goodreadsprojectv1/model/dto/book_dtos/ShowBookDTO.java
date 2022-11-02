package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShowBookDTO {
    private long isbn;
    private String name;
    private String content;
    private String bookCover;
    private String additionalInfo;
    private AuthorWithoutRelationsDTO author;
    private List<GenreWithoutBooksDTO> genres=new ArrayList<>();
}
