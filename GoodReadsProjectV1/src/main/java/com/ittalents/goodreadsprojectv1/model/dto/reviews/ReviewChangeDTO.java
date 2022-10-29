package com.ittalents.goodreadsprojectv1.model.dto.reviews;

import lombok.Data;


@Data
public class ReviewChangeDTO {
    private int id;
    private int rate;
    private String opinion;
}
