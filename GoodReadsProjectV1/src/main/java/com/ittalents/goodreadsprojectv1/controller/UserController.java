package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.dto.ErrorDTO;
import com.ittalents.goodreadsprojectv1.model.dto.UserDTO;
import com.ittalents.goodreadsprojectv1.model.entity.User;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
public class UserController extends ExceptionController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/users")
    public User register(@RequestBody User u) {
//        String email = u.getEmail();
//        UserController.checkEmail(email);
        if (userRepository.findAllByEmail(u.getEmail()).size() > 0) {
            throw new BadRequestException(" there is already registered user with this email ");
        }
//        String pass = u.getPass();
//        UserController.checkPassword(pass);
        u.setLastEnter(LocalDateTime.now());
        u.setMemberFrom(LocalDateTime.now());
        userRepository.save(u);
        return u;
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> usersDTO = users.stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
        return usersDTO;
    }

    @GetMapping("/users/{id}")
    public UserDTO getUserById(@PathVariable long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            UserDTO dto = modelMapper.map(u, UserDTO.class);
            return dto;
        } else {
            throw new NotFoundException("not found exception");
        }
    }


}
