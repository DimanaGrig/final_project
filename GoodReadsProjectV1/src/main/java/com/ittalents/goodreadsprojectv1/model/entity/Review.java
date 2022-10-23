package com.ittalents.goodreadsprojectv1.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name ="reviews")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column
    private int rate;
    @Column
    private String opinion;
    @ManyToMany(mappedBy = "likedReviews")
    private Set<User> usersLikedThisReview;
    @OneToMany(mappedBy = "commentReview")
    List<Comment> commentsForThisReview;

}
