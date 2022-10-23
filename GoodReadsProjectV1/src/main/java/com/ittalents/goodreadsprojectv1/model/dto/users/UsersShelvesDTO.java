package com.ittalents.goodreadsprojectv1.model.dto.users;

import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfWithoutRelationsDTO;

import java.util.List;

public class UsersShelvesDTO {
    private int id;
    private String firstName;
    private String lastNme;
    private String photo;
    private List<ShelfWithoutRelationsDTO> userShelves;
}
