package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.*;
import com.ittalents.goodreadsprojectv1.model.entity.User;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
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
            UserRespWithoutPassDTO dto1 = modelMapper.map(u, UserRespWithoutPassDTO.class);
            dto1.setUserShelves(u.getUserShelves().stream().map(sh -> modelMapper.map(sh, ShelfWithoutRelationsDTO.class)).collect(Collectors.toList()));
            return dto1;
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
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pass);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }


    public UserWithoutRelationsDTO login(UserReqLoginDTO dto) {
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
                return modelMapper.map(u, UserWithoutRelationsDTO.class);
            }
        }
        throw new UnauthorizedException("Wrong Credentials!");
    }


    public UserRespWithoutPassDTO getById(int uid) {
        User u = getUserById(uid);
        UserRespWithoutPassDTO dto = modelMapper.map(u, UserRespWithoutPassDTO.class);
        return dto;
    }

    public List<UserWithoutRelationsDTO> findAll() {
        List<User> users = userRepository.findAll();
        if(users.size()==0){
            throw new NotFoundException("We don't have any users at all.");
        }
        return users.stream().map(u -> modelMapper.map(u, UserWithoutRelationsDTO.class)).collect(Collectors.toList());
    }


    public void delete(int id) {
        userRepository.deleteById(id);
        System.out.println("the user with id = " + id + " have been deleted!");
    }


    public UserFollowingDTO followUser(int fid, int id) {
        User following = getUserById(fid);
        User follower = getUserById(id);
        if (follower.getFollowing().contains(following)) {
            follower.getFollowing().remove(following);
        } else {
            follower.getFollowing().add(following);
        }
        userRepository.save(follower);
        UserFollowingDTO u = modelMapper.map(follower, UserFollowingDTO.class);
        u.setFollowing(follower.getFollowing().stream().map(f -> modelMapper.map(f, UserWithoutRelationsDTO.class)).collect(Collectors.toList()));
        return u;
    }

    public UserRespWithoutPassDTO changePass(UserReqChangePassDTO dto, int id) {
        String newPass = dto.getNewPassword();
        String confirmNewPass = dto.getConfirmNewPassword();
        if (newPass.equals(confirmNewPass)) {
            User user = getUserById(id);
            if (bCryptPasswordEncoder.matches(dto.getPassword(), user.getPass()) && validatePass(dto.getNewPassword())) {
                user.setPass(bCryptPasswordEncoder.encode(dto.getNewPassword()));
                userRepository.save(user);
                UserRespWithoutPassDTO dto1 = modelMapper.map(user, UserRespWithoutPassDTO.class);
                return dto1;
            }
        }
        throw new BadRequestException("Not good Credentials!");
    }

    public UserRespWithoutPassDTO editProfile(UserEditProfile dto, int id) {
        User user = getUserById(id);
        String firstName = dto.getFirstName();
        if (firstName != null) {
            user.setFirstName(firstName);
        }
        String lastName = dto.getLastName();
        if (lastName != null) {
            user.setLastName(lastName);
        }
        String gender = dto.getGender();
        if (gender != null) {
            user.setGender(gender);
        }
        String website = dto.getWebsite();
        if (website != null) {
            user.setWebsite(website);
        }
        String infoForUser = dto.getInfoForUser();
        if (infoForUser != null) {
            user.setInfoForUser(infoForUser);
        }
        String address = dto.getAddress();
        if (address != null) {
            user.setAddress(address);
        }
        userRepository.save(user);
        UserRespWithoutPassDTO dto1 = modelMapper.map(user, UserRespWithoutPassDTO.class);
        return dto1;
    }

    public UserWithoutRelationsDTO findUserByEmail(String email) {
        if (!validateEmail(email)) {
            throw new BadRequestException("Wrong Credentials");
        }
        User user = findByEmail(email);
        return modelMapper.map(user, UserWithoutRelationsDTO.class);
    }

    public UserShelvesDTO findAllUserShelves(int uid) {
        User u = getUserById(uid);
        UserShelvesDTO dto = modelMapper.map(u, UserShelvesDTO.class);
        dto.setUserShelves(u.getUserShelves().stream().map((sh -> modelMapper.map(sh, ShelfWithoutRelationsDTO.class))).collect(Collectors.toList()));
        return dto;
    }

    public List<UserWithoutRelationsDTO> findByName(String name) {
        List<User> users = findAllByName(name);
        return users.stream().map(user -> modelMapper.map(user, UserWithoutRelationsDTO.class)).collect(Collectors.toList());
    }

    public Page<UserWithoutRelationsDTO> getAll(int page,int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<User>  users =userRepository.findAll(pageable);
        Page<UserWithoutRelationsDTO> usersDTO =users.map(user -> modelMapper.map(user,UserWithoutRelationsDTO.class));
        return usersDTO;
    }

}