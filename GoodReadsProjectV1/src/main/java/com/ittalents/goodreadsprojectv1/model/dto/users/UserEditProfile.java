package com.ittalents.goodreadsprojectv1.model.dto.users;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserEditProfile {
    private String firstName;
    private String lastName;
    private String gender;
    private String website;
    private String infoForUser;
    private String address;

}
