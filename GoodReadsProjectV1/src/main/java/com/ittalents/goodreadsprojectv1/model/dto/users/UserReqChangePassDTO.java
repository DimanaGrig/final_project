package com.ittalents.goodreadsprojectv1.model.dto.users;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class UserReqChangePassDTO {
    @NotNull
    private String password;
    @NotNull
    private String newPassword;
    @NotNull
    private String confirmNewPassword;
}
