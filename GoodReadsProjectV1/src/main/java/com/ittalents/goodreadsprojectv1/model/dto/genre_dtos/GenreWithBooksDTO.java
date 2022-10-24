package com.ittalents.goodreadsprojectv1.model.dto.genre_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookWithoutGenresDTO;
import lombok.Data;

import java.util.List;

@Data
public class GenreWithBooksDTO {
    private int id;
    private String name;
    private List<BookWithoutGenresDTO> booksInGenre;
}
