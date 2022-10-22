package com.ittalents.goodreadsprojectv1.model.dto.author_dtos;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class AddAuthorDTO {
    private String firstName;
    private String lastName;
    private String picture;
    private String informationForAuthor;
    private String authorWebsite;
    //used only in methods when s.o. uploads a book and the author is not already in the database
}
