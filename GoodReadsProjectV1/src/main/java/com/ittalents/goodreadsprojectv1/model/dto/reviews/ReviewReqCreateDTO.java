package com.ittalents.goodreadsprojectv1.model.dto.reviews;

import com.ittalents.goodreadsprojectv1.model.entity.Book;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class ReviewReqCreateDTO {
    private long bookIsbn ;
    private int shelfId;
    private String shelfName;
    @Min(0)
    @Max(5)
    private int rate;
    private String opinion;

}
