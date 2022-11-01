package com.ittalents.goodreadsprojectv1.model.dto.reviews;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookWithoutRelationsDTO;
import lombok.Data;

@Data
public class ReviewRespFriendDTO {
    private int id;
    private int rate;
    private String opinion;
    private BookWithoutRelationsDTO book;
}
