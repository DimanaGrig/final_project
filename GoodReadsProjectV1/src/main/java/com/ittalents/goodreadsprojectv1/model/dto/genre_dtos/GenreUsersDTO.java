package com.ittalents.goodreadsprojectv1.model.dto.genre_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Genre;
import lombok.Data;

import java.util.Set;

@Data
public class GenreUsersDTO {

    private int id;
    private String name;
    private Set<UserWithoutRelationsDTO> userLikedGenre;
}
