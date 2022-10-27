package com.ittalents.goodreadsprojectv1.model.dto.author_dtos;

import lombok.Data;

@Data
public class AuthorWithoutRelationsDTO {
    private int id;
    private String firstName;
    private String lastName;
}
