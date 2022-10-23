package com.ittalents.goodreadsprojectv1.model.dto.users;

import lombok.Data;

@Data
public class UserRequRegisterDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String pass;
    private String confirmPass;
    private String gender;
    private String address;
    private String website;
    private String infoForUser;

}
