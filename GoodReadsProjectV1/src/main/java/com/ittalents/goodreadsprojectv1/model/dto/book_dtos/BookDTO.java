package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import lombok.Data;

@Data
public class BookDTO {
    private long ISBN;
    private String name;
    private String title;
    private String cover;
    private String content;
    private int rate;
    private String additionalInfo;


    //ще съдържа всички полета на книгата, т.е. трябва ли да съществува? от съображения за симетрия


    //     Post post = modelMapper.map(postDto, Post.class);
    //vs
    // UserDTO dto = modelMapper.map(u, UserDTO.class);
    //превръщане от ДТО в ентити и обратното
}
