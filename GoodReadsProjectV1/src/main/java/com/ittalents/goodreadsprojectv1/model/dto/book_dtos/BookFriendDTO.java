package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import lombok.Data;

@Data
public class BookFriendDTO {
    private long isbn;
    private String name;
    private String bookCover;
}
