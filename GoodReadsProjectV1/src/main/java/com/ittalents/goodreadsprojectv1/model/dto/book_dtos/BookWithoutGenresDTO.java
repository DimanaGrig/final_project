package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.quote_dtos.QuoteWithoutBookDTO;
import lombok.Data;

@Data
public class BookWithoutGenresDTO {
    private long ibsn;
    private String name;
    private String content;
    private String additionalInfo;
    private QuoteWithoutBookDTO quotes;
    private AuthorWithoutBooksDTO author;
}
