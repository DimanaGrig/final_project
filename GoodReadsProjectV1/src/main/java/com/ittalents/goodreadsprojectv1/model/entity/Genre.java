package com.ittalents.goodreadsprojectv1.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "genres")
@Data
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @ManyToMany(mappedBy = "likedGenres")
    private List<User> userLikedGenre;
    @ManyToMany(mappedBy = "bookGenres")
    private List<Book> booksInGenre;
}
