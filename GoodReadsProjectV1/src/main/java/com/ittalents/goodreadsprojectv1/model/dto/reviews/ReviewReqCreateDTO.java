package com.ittalents.goodreadsprojectv1.model.dto.reviews;

import com.ittalents.goodreadsprojectv1.model.entity.Book;
import lombok.Data;

@Data
public class ReviewReqCreateDTO {

    private int rate;
    private String opinion;
    private Book book;
}
