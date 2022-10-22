package com.ittalents.goodreadsprojectv1.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "quotes")
@Data
public class Quote {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column
    private String content;
    @Column
    private long bookId;
    @ManyToOne
    @JoinColumn(name = "book_id" /*, referencedColumnName="isbn"*/) //todo - more research on this one
    private Book book;
}
