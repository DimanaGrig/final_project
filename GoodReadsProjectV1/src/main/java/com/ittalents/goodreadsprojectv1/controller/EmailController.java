package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.EmailDTO.EmailDTO;
import com.ittalents.goodreadsprojectv1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class EmailController extends AbstractController {
    @Autowired
    private UserService userService;
    @PostMapping("/send")
    public void sendMail(@RequestBody EmailDTO dto, HttpServletRequest request) {
        userService.sendSimpleMessage(dto);
        return;
    }
}
