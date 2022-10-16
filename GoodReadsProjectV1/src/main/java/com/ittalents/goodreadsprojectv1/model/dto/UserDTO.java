package com.ittalents.goodreadsprojectv1.model.dto;

import lombok.Data;


import java.time.LocalDateTime;

@Data
public class UserDTO {

    private String firstName;
    private String lastName;
    private String infoForUser;
    private LocalDateTime memberFrom;
    private LocalDateTime lastEnter;
}
