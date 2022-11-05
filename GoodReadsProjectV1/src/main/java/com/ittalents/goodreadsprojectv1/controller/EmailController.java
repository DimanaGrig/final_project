package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.EmailDTO.EmailDTO;
import com.ittalents.goodreadsprojectv1.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class EmailController extends AbstractController {

    @Autowired
    private EmailService еmailService;

    @PostMapping("/send")
    public void sendMail(@RequestBody EmailDTO dto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        еmailService.sendSimpleMessage(dto);
        return;
    }
}
