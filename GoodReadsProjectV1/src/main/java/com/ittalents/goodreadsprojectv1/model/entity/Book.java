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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long isbn;
    @Column
    private String name;
    @Column
    private String bookCover;
    @Column
    private String content;
    @Column
    private String additionalInfo;
    @OneToMany(mappedBy = "book")
    private List<Review> reviewsForBook;
    @ManyToMany(mappedBy = "booksAtThisShelf")
    private List<Shelf> shelvesOwnThisBook;
    @ManyToMany
    @JoinTable(
            name = "genres_of_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> bookGenres;
    @ManyToOne
    @JoinColumn(name = "author_id") /*, referencedColumnName="isbn"*/ //todo - dont forget to research this
    private Author author;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

}
