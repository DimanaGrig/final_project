package com.ittalents.goodreadsprojectv1.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "commented_by")
    private User user;
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review commentReview;
    @Column
    private String comment;
    @Column
    private LocalDateTime commentedAt;
}
