package com.ittalents.goodreadsprojectv1.model.dto.genre_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookWithoutGenresDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class GenreDTO {
    private int id;
    private String name;
    private List<BookWithoutGenresDTO> booksInGenre;

}
