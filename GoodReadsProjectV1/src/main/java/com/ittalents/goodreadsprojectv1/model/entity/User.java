package com.ittalents.goodreadsprojectv1.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String pass;
    @Column
    private String gender;
    @Column
    private LocalDateTime memberFrom;
    @Column
    private LocalDateTime lastEnter;
    @Column(name = "addres")
    private String address;
    @Column
    private String website;
    @Column
    private String infoForUser;
    @Column
    private String photo;
    @ManyToMany
    @JoinTable(
            name = "users_like_genres",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> likedGenres;
    @OneToMany(mappedBy = "user")
    private List<Review> writtenReviews = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "likes_review",
            joinColumns = @JoinColumn(name = "user_id_liked_review"),
            inverseJoinColumns = @JoinColumn(name = "review_id"))
    private List<Review> likedReviews;
    @OneToMany(mappedBy = "user")
    private List<Comment> userCommentsForReviews = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Shelf> userShelves = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "list_of_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follоwer_id"))
    private List<User> following;
    @ManyToMany(mappedBy = "following")
    private List<User> followers;
}
