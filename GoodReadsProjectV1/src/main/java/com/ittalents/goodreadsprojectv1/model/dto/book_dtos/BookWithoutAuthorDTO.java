package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.quote_dtos.QuoteWithoutBookDTO;
import lombok.Data;

@Data
public class BookWithoutAuthorDTO {
    private long ibsn;
    private String name;
    private String content;
    private String additionalInfo;
//    spored men tezi trqbwa da  gi nqma tuk ili pone da sa v kolekciya.
    private QuoteWithoutBookDTO quotes;
    private GenreWithoutBooksDTO genres;
}
