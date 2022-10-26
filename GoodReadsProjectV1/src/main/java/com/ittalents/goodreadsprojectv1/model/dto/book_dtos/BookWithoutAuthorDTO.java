package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.quote_dtos.QuoteWithoutBookDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class BookWithoutAuthorDTO {
    private long isbn;
    private String name;
    private String content;
    private UserWithoutRelationsDTO owner;
    private String additionalInfo;
//    spored men tezi trqbwa da  gi nqma tuk ili pone da sa v kolekciya.
    private List<QuoteWithoutBookDTO> quotes;
    private Set<GenreWithoutBooksDTO> genres;

}
