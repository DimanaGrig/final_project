package com.ittalents.goodreadsprojectv1.model.dto.shelves;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import lombok.Data;

import java.util.Set;

@Data
public class ShelfDTO {
//    for now not sure where to use this :)
    private  int id;
    private String name;
    private Set<BookWithoutRelationsDTO> booksAtThisShelf;
    private UserWithoutRelationsDTO user;
}
