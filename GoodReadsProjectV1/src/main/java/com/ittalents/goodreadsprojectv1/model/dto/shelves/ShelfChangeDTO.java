package com.ittalents.goodreadsprojectv1.model.dto.shelves;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookDTO;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import lombok.Data;

import java.util.List;
@Data
public class ShelfChangeDTO {
    private int id;
    private String name ;
    private String newName;
    private BookDTO book;

}
