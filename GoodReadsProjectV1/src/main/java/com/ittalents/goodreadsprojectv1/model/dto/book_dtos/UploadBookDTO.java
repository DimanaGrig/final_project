package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UploadBookDTO {
    private long isbn;
    private String name;
    private String content;
    private int authorId;
    private String additionalInfo;
    private List<Integer> genres=new ArrayList<>(); //genresIds
}
