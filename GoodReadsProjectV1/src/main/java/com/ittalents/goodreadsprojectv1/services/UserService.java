package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserReqLoginDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserReqRegisterDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserRespWithoutPassDTO;
import com.ittalents.goodreadsprojectv1.model.entity.User;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService {
    @Autowired
    private ShelfService shelfService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public UserRespWithoutPassDTO register(UserReqRegisterDTO dto) {
        String email = dto.getEmail();
        String pass = dto.getPass();
        if (!dto.getPass().equals(dto.getConfirmPass())) {
            throw new BadRequestException("Password mismatches!");
        }

        if (validateEmail(email) && checkForEmailInDB(email) && validatePass(pass)) {
            User u = modelMapper.map(dto, User.class);
            u.setPass(bCryptPasswordEncoder.encode(u.getPass()));
            u.setMemberFrom(LocalDateTime.now());
            u.setLastEnter(LocalDateTime.now());
            userRepository.save(u);
            shelfService.createBasicShelves(u);
            return modelMapper.map(u, UserRespWithoutPassDTO.class);
        }
        throw new BadRequestException("Not good Credentials!");
    }


    private boolean validateEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return false;
//          throw new BadRequestException("This email is bad.");
        }
        return true;

    }

    private boolean checkForEmailInDB(String email) {
        if (userRepository.findAllByEmail(email).size() > 0) {
            return false;
        }
        return true;
    }

    private boolean validatePass(String pass) {
        String regex ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pass);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }


    public UserRespWithoutPassDTO login(UserReqLoginDTO dto) {
        String email = dto.getEmail();
        String pass = dto.getPass();
        if (!validateEmail(email) || !validatePass(pass)) {
            throw new BadRequestException("Wrong Credentials");
        }
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            User u = user.get();
            if (bCryptPasswordEncoder.matches(pass, u.getPass())) {
                u.setLastEnter(LocalDateTime.now());
                userRepository.save(u);
                return modelMapper.map(u, UserRespWithoutPassDTO.class);
            }
            throw new UnauthorizedException("Wrong Credentials!");
        } else {
            throw new UnauthorizedException("Wrong Credentials!");
        }
    }

    public UserRespWithoutPassDTO getById(int uid) {
        User u = getUserById(uid);
        UserRespWithoutPassDTO dto = modelMapper.map(u, UserRespWithoutPassDTO.class);
        dto.setUserShelves(u.getUserShelves().stream().map(sh -> modelMapper.map(sh, ShelfWithoutRelationsDTO.class)).collect(Collectors.toList()));
//        same for reviews,comments,ect..TODO
        return dto;
    }


}