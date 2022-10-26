package com.ittalents.goodreadsprojectv1.model.dto.shelves;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ShelfsBooksDTO {
    private int id;
    private String name;

    private List<BookWithoutRelationsDTO> booksAtThisShelf;


}
