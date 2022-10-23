package com.ittalents.goodreadsprojectv1.controller;


import com.ittalents.goodreadsprojectv1.model.dto.users.UserRequLoginDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserRequRegisterDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserRespWithoutPassDTO;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserController extends AbstractController {
    @Autowired
    private UserService userService;


    @PostMapping("/users")
    public UserRespWithoutPassDTO register(@RequestBody UserRequRegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/auth")
    public UserRespWithoutPassDTO login(@RequestBody UserRequLoginDTO dto, HttpServletRequest request) {
        if (getLoggedUserId(request) > 0) {
            throw new BadRequestException("You already have logged!");
        }
        UserRespWithoutPassDTO response = userService.login(dto);
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
}

