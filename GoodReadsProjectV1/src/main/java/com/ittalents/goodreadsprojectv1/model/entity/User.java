package com.ittalents.goodreadsprojectv1.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;
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
    private String website;
    @Column
    private String infoForUser;
    @Column
    private LocalDateTime memberFrom;
    @Column
    private LocalDateTime lastEnter;
    @Column
    private String address;

}
