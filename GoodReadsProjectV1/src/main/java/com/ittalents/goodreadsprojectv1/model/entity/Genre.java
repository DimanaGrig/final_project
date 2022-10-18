package com.ittalents.goodreadsprojectv1.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "genres")
@Data
public class Genre {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;

}
