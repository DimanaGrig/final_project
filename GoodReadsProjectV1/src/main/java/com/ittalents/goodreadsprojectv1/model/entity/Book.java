package com.ittalents.goodreadsprojectv1.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long isbn;
    @Column
    private String name;
    @Column
    private int authorId;
    @Column
    private String content;
    @Column
    private int languageId;
    @Column
    private String additionalInfo;
    @Column
    private Date dateOfPublish;
    @Column
    private String rate;
    @OneToMany(mappedBy = "book")
    List<Review> reviewsForBooks;
    @ManyToMany(mappedBy = "booksAtThisShelf")
    private Set<Shelf> shelvesOwnThisBook;

}
