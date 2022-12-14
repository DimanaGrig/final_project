package com.ittalents.goodreadsprojectv1.model.dto.reviews;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.comments.CommentWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ReviewDTO {

    private int id;
    private int rate;
    private String opinion;
    private BookWithoutRelationsDTO book;
    private UserWithoutRelationsDTO user;
    private List<CommentWithoutRelationsDTO> commentsForThisReview;

}
