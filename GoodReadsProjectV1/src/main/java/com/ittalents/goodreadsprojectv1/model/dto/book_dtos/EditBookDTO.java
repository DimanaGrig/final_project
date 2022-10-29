package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import lombok.Data;

import java.util.List;

@Data
public class EditBookDTO {
    //private long isbn;
    private String content;
    private String cover;
    private String additionalInfo;
    private List<GenreWithoutBooksDTO> genres;
}
