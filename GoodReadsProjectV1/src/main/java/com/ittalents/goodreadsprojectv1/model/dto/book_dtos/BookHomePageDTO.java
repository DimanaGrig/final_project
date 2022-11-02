package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutRelationsDTO;
import lombok.Data;

@Data
public class BookHomePageDTO {
    private long isbn;
    private String name;
    private double avrRate;
    private String cover;
    private AuthorWithoutRelationsDTO author;
}
