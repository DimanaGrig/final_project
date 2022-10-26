package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import lombok.Data;

import java.util.Set;

@Data
public class ShowBookDTO {
    private long isbn;
    private String name;
    private UserWithoutRelationsDTO owner;
    private String content;
    private String cover;
    private String additionalInfo;
    private AuthorWithoutBooksDTO author;
    private Set<GenreWithoutBooksDTO> genres;

}
