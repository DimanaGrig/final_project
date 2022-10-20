package com.ittalents.goodreadsprojectv1.model.dto;

import lombok.Data;

@Data
public class BookDTO {
    private String name;
    private String title;
    private String cover;
    private int rate;
    private String additionalInfo;



    // трябват ли ни Repositories, специфични за всяко entity - супер много класове
    //     Post post = modelMapper.map(postDto, Post.class);
    //vs
    // UserDTO dto = modelMapper.map(u, UserDTO.class);
//превръщане от ДТО в ентити и обратното
}
