package com.ittalents.goodreadsprojectv1.model.entity;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;

@Entity
@Table(name="authors")
@Data
public class Author {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String picture;
    @Column
    private String informationForAuthor;
    @Column
    private String authorWebsite;
}
