package com.ittalents.goodreadsprojectv1.model.dto.users;

import lombok.Data;

import java.util.Set;

@Data
public class UsersFollowersDTO {
    private int id;
    private String firstName;
    private String lastNme;
    private String photo;
     private Set<UserWithoutRelationsDTO> followers;

}
