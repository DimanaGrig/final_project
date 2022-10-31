package com.ittalents.goodreadsprojectv1.controller;


import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.*;
import com.ittalents.goodreadsprojectv1.model.entity.User;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import com.ittalents.goodreadsprojectv1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController extends AbstractController {
    @Autowired
    private UserService userService;



    @PostMapping("/users")
    public UserRespWithoutPassDTO register(@RequestBody UserReqRegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/auth")
    public UserWithoutRelationsDTO login(@RequestBody UserReqLoginDTO dto, HttpServletRequest request) {
        if (getLoggedUserId(request) > 0) {
            throw new BadRequestException("You already have logged!");
        }
        UserWithoutRelationsDTO response = userService.login(dto);
        if (response != null) {
            logUser(request, response.getId());
            return response;
        } else {
            throw new BadRequestException("Wrong credentials!");
        }
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }


    //                          ----DELETE USER
    @DeleteMapping("/users")
    public void deleteUser(HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        userService.delete(id);
        request.getSession().invalidate();
    }

    //                          ----FOLLOW USER
      @PutMapping("/users")
    public UserFollowingDTO followUser(@RequestParam int fid, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        if (fid == id) {
            throw new UnauthorizedException("You can't follow yourself, narcissus!");
        }
        return userService.followUser(fid, id);
    }

    //                          ----GET ALL USERS
    @GetMapping("/users")
    public List<UserWithoutRelationsDTO> getAllUsers() {
        return userService.findAll();
    }



    //                          ----CHANGE PASS
    @PutMapping("/users/sth")
    public UserRespWithoutPassDTO changePass(@RequestBody UserReqChangePassDTO dto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return userService.changePass(dto, id);

    }

    //                          ----GET BY ID
    @GetMapping("/users/sth")
    public UserRespWithoutPassDTO getById(@RequestParam int uid) {
        return userService.getById(uid);
    }

//                          ----EDIT PROFILE

    @PutMapping("/users/user")
    public UserRespWithoutPassDTO editProfile(@RequestBody UserEditProfile dto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return userService.editProfile(dto, id);
    }

    //                          ----GET USER BY EMAIL
    @GetMapping("users/user")
    public UserWithoutRelationsDTO getUserByEmail(@RequestParam String email) {
        return userService.findUserByEmail(email);
    }

    //                          ----GET USER SHELVES
    @GetMapping("/users/sh")
    public UserShelvesDTO getAllUserShelves(@RequestParam int uid) {
        return userService.findAllUserShelves(uid);

    }

    //                          ----GET USER by firstName
    @GetMapping("users/users")
    public List<UserWithoutRelationsDTO> findUsersByName(@RequestParam String name) {
        return userService.findByName(name);
    }

    @GetMapping("/users/page")
    public Page<UserWithoutRelationsDTO> getAll(@RequestParam int page,@RequestParam int size){
        return  userService.getAll(page,size);
    }

    //                          ----GET USER Followers


//                          ----GET USER Following


//                          ----GET USER likedGenres



//                          ----GET USER rating avr


//                          ----GET USER rating total


//                          ----GET USER reviews(sum)


//                          ----GET USER comments


//                          ----GET USER reviews


}


