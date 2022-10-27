package com.ittalents.goodreadsprojectv1.controller;


import com.ittalents.goodreadsprojectv1.model.dto.users.*;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import com.ittalents.goodreadsprojectv1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/users/{uid}")
    public UserRespWithoutPassDTO getById(@PathVariable int uid) {
        return userService.getById(uid);
    }

    @GetMapping("/users")
    public List<UserWithoutRelationsDTO> getAllUsers() {
        return userService.findAll();
    }

    @DeleteMapping("/users/delete")
    public void deleteUser(HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        userService.delete(id);
        request.getSession().invalidate();
    }

    @PostMapping("/users/{fid}/follow")
    public UserFollowingDTO followUser(@PathVariable int fid, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        if(fid==id){
            throw  new UnauthorizedException("You can't follow yourself, narcissus!");
        }
        return userService.followUser(fid, id);
    }

    @PutMapping("/users/changePass")
    public UserRespWithoutPassDTO changePass(@RequestBody UserReqChangePassDTO dto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return userService.changePass(dto, id);

    }


    @PutMapping("/users/{uid}/edit")
    public UserRespWithoutPassDTO editProfile(@RequestBody UserEditProfile dto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return userService.editProfile(dto, id);
    }

}


