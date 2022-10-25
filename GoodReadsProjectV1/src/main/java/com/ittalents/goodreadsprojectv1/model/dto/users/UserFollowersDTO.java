package com.ittalents.goodreadsprojectv1.model.dto.users;

import lombok.Data;

import java.util.List;

@Data
public class UserFollowersDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String photo;
     private List<UserWithoutRelationsDTO> followers;

}
