package com.ittalents.goodreadsprojectv1.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @Column(name ="addres")
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
    private Set<Genre> likedGenres;
    @OneToMany(mappedBy = "user")
    private List<Review> writtenReviews;
    @ManyToMany
    @JoinTable(
            name = "likes_review",
            joinColumns = @JoinColumn(name = "user_id_liked_review"),
            inverseJoinColumns = @JoinColumn(name = "review_id"))
    private Set<Review> likedReviews;
    @OneToMany(mappedBy = "user")
    private List<Comment> userCommentsForReviews;
    @OneToMany(mappedBy = "user")
    private List<Shelf> userShelves;
    @ManyToMany
    @JoinTable(
            name = "list_of_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private Set<User> followers;
    @ManyToMany(mappedBy = "followers")
    private Set<User> following;


}
