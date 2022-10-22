package com.ittalents.goodreadsprojectv1.model.dto.quote_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import lombok.Data;

@Data
public class QuoteDTO {
    private String content;
    private BookDTO book;
}
