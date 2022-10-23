package com.ittalents.goodreadsprojectv1.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "shelves")
@Data
public class Shelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @Column
    private String name;
    @Column(name = "from_beggining")
    private boolean fromBeggining = false;
    @ManyToOne
    @JoinColumn(name ="user_id" )
    private User user;
    @ManyToMany
    @JoinTable(
            name = "books_at_shelves",
            joinColumns = @JoinColumn(name = "shelf_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> booksAtThisShelf;
}
