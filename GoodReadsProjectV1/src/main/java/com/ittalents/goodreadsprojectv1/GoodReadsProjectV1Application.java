package com.ittalents.goodreadsprojectv1;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(exclude ={SecurityAutoConfiguration.class})
public class GoodReadsProjectV1Application {

    public static void main(String[] args) {
        SpringApplication.run(GoodReadsProjectV1Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public NamedParameterJdbcTemplate jdbcTemplate() {
//        return new NamedParameterJdbcTemplate();
//    }
}
