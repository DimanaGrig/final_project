package com.ittalents.goodreadsprojectv1.model.dto.users;

import lombok.Data;

@Data
public class UserReqLoginDTO {
    private String email;
    private  String  pass;
}
