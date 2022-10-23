package com.ittalents.goodreadsprojectv1.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDBDAO implements UserDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;
    //demo
    /*@Override
    * public User insert(User u){
    *   jdbcTemplate.execute(-SQL statement-)
    * /.../
    * }
    * for more inf: https://www.baeldung.com/spring-jdbc-jdbctemplate
    * */
}
