package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import lombok.Data;

@Data
public class BookWithoutRelationsDTO {
//    za shelf
    private long id;
    private String name;
    private UserWithoutRelationsDTO owner;
    private String bookCover;

//    get rate from reviews!
}
