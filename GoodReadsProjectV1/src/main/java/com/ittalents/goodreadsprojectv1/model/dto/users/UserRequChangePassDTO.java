package com.ittalents.goodreadsprojectv1.model.dto.users;

import lombok.Data;

@Data
public class UserRequChangePassDTO {
    private String password;
    private  String newPassword;
    private String confirmNewPassword;

}
