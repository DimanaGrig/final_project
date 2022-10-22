package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import lombok.Data;

@Data
public class EditUserBookDTO {
    //edit the books that you posted
    //what one can edit
    private String content;
    private String additionalInfo;
}
