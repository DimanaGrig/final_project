package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import com.ittalents.goodreadsprojectv1.model.entity.*;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Data
public class BookHelpDTO {
        private long isbn;
        private String name;
        private String bookCover;
        private Author author;
        private double avrRate ;

    }
