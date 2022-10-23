package com.ittalents.goodreadsprojectv1.model.dto.users;

import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import lombok.Data;

import java.util.Set;

@Data
public class UsersGenresDTO {
    private int id;
    private String firstName;
    private String lastNme;
    private String photo;
    private Set<GenreWithoutBooksDTO> likedGenres;
}
