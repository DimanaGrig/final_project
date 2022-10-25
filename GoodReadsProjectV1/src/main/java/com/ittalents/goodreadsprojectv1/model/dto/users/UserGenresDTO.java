package com.ittalents.goodreadsprojectv1.model.dto.users;

import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserGenresDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String photo;
    private List<GenreWithoutBooksDTO> likedGenres;
}
