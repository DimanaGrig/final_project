package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.quote_dtos.QuoteWithoutBookDTO;
import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewsCommentsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.entity.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class BookDTO {
    private long isbn;
    private String name;
    private String content;
    private String cover;
    private String additionalInfo;
    private UserWithoutRelationsDTO owner;
    private List<QuoteWithoutBookDTO> quotes;
    private List<GenreWithoutBooksDTO> genres;
    private AuthorWithoutBooksDTO author;
    private List<ReviewsCommentsDTO> reviewsForBooks;
    private List<ShelfWithoutRelationsDTO> shelvesOwnThisBook;
}
