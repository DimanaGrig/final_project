package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreDTO;
import com.ittalents.goodreadsprojectv1.model.dto.quote_dtos.QuoteDTO;
import com.ittalents.goodreadsprojectv1.model.entity.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class BookDTO {
    private String name;
    private String content;
    private String additionalInfo;
    private QuoteDTO quotes;
    private GenreDTO genres;
    private AuthorDTO author;
    // todo - rewrite the following fields as the proper DTOs
    private List<Review> reviewsForBooks;
    private Set<Shelf> shelvesOwnThisBook;

    //==show book
// todo
    //Post post = modelMapper.map(postDto, Post.class);
    //vs
    // UserDTO dto = modelMapper.map(u, UserDTO.class);
    //превръщане от ДТО в ентити и обратното
}
