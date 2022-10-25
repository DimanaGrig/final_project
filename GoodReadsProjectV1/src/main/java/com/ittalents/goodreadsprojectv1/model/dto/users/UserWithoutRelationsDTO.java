package com.ittalents.goodreadsprojectv1.model.dto.users;

import lombok.Data;

@Data
public class UserWithoutRelationsDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String photo;
}
