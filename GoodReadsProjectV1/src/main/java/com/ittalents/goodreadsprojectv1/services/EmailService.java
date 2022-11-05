package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.EmailDTO.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

        @Autowired
        private JavaMailSender emailSender;

        public void sendSimpleMessage(
                EmailDTO dto) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ittalentsfinalpr@gmail.com");
            message.setTo(dto.getTo());
            message.setSubject(dto.getSubject());
            message.setText(dto.getMessage());
            emailSender.send(message);


    }
}
