package com.ittalents.goodreadsprojectv1.model.dto.users;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class UserFollowersDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String photo;
    private List<UserRespFriendDTO> following;

}
