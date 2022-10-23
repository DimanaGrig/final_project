package com.ittalents.goodreadsprojectv1.model.dto.shelves;

import lombok.Data;

@Data
public class ShelfWithoutRelationsDTO {
    private  int id;
    private String name;
    private boolean isDefaut;
}