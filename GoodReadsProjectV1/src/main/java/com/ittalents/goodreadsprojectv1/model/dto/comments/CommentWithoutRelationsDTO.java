package com.ittalents.goodreadsprojectv1.model.dto.comments;


import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CommentWithoutRelationsDTO {
    private int id;
    private String comment;
    private LocalDateTime commentedAt;

}
