package com.ittalents.goodreadsprojectv1.model.dto.author_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookWithoutAuthorDTO;
import lombok.Data;

import java.util.List;

@Data
public class EditAuthorDTO {
   // private int id;
    private String informationForAuthor;
    private String authorWebsite;
}
