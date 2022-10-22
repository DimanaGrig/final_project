package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreDTO;
import lombok.Data;

@Data
public class UploadBookDTO {
    private String name;
    private String content;
    private String additionalInfo;
    private AuthorDTO author;
    private GenreDTO genres;


}
