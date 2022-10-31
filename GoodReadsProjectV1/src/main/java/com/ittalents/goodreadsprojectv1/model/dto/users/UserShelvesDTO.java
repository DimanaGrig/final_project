package com.ittalents.goodreadsprojectv1.model.dto.users;

import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfWithoutRelationsDTO;
import lombok.Data;

import java.util.List;
@Data
public class UserShelvesDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String photo;
    private List<ShelfWithoutRelationsDTO> userShelves;
}
