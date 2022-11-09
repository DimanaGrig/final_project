package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.EmailDTO.EmailDTO;
import com.ittalents.goodreadsprojectv1.model.dao.UserDAO;
import com.ittalents.goodreadsprojectv1.model.dto.comments.CommentWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.*;
import com.ittalents.goodreadsprojectv1.model.entity.User;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
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

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public UserRespWithoutPassDTO register(UserReqRegisterDTO dto) {
        String email = dto.getEmail();
        String pass = dto.getPass();
        if (!((validateEmail(email)) || checkForEmailInDB(email) || validatePass(pass))) {
            throw new BadRequestException("Not good Credentials!");
        }
        if (!dto.getPass().equals(dto.getConfirmPass())) {
            throw new BadRequestException("Password mismatches!");
        }
        if (!validateSize(dto.getInfoForUser())) {
            throw new BadRequestException("Too many characters!");
        }
        if (!(validateLength(dto.getFirstName().length()) || validateLength(dto.getLastName().length()))) {
            throw new BadRequestException("Too many characters!");
        }
        User u = modelMapper.map(dto, User.class);
        u.setPass(bCryptPasswordEncoder.encode(u.getPass()));
        u.setMemberFrom(LocalDateTime.now());
        u.setLastEnter(LocalDateTime.now());
        userRepository.save(u);
        shelfService.createBasicShelves(u);
        UserRespWithoutPassDTO dto1 = modelMapper.map(u, UserRespWithoutPassDTO.class);
        dto1.setUserShelves(u.getUserShelves().stream().map(sh -> modelMapper.map(sh, ShelfWithoutRelationsDTO.class)).collect(Collectors.toList()));
        EmailDTO emailDto=new EmailDTO();
        emailDto.setTo(u.getEmail());
        emailDto.setSubject(REGISTRATION_SUBJECT);
        emailDto.setMessage(u.getFirstName()+" "+u.getLastName()+REGISTRATION_MESSAGE);
        sendSimpleMessage(emailDto);
        return dto1;
    }


    private boolean validateEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return false;
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
        if (users.size() == 0) {
            throw new NotFoundException("We don't have any users at all.");
        }
        return users.stream().map(u -> modelMapper.map(u, UserWithoutRelationsDTO.class)).collect(Collectors.toList());
    }
    public void delete(int id) {
        userRepository.deleteById(id);
        System.out.println("the user with id = " + id + " has been deleted!");
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
        if (dto.getFirstName() != null && dto.getLastName()!=null &&
                dto.getGender()!=null  && dto.getAddress()!=null ) {
            checkForLength(dto.getFirstName());
            checkForLength(dto.getLastName());
            checkForLength(dto.getGender());
            checkForLength(dto.getAddress());
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setGender(dto.getGender());
            user.setAddress(dto.getAddress());
        }else{
            throw new BadRequestException("You doesn't fill the requierd data.");
        }
        String website = dto.getWebsite();
        if (website != null) {
            user.setWebsite(website);
        }
        String infoForUser = dto.getInfoForUser();
        if (infoForUser != null) {
            user.setInfoForUser(infoForUser);
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

    public Page<UserWithoutRelationsDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
        Page<UserWithoutRelationsDTO> usersDTO = users.map(user -> modelMapper.map(user, UserWithoutRelationsDTO.class));
        return usersDTO;
    }

    public UserReviewsDTO getUserReviews(int uid) {
        User user = getUserById(uid);
        UserReviewsDTO dto = modelMapper.map(user, UserReviewsDTO.class);
        dto.setWrittenReviews(user.getWrittenReviews().stream().map(re -> modelMapper.map(re, ReviewWithoutRelationsDTO.class)).collect(Collectors.toList()));
        return dto;
    }

    public UserCommentsDTO getUserComments(int uid) {
        User user = getUserById(uid);
        UserCommentsDTO dto = modelMapper.map(user, UserCommentsDTO.class);
        dto.setUserCommentsForReviews(user.getUserCommentsForReviews().stream().map(co -> modelMapper.map(co, CommentWithoutRelationsDTO.class)).collect(Collectors.toList()));
        return dto;
    }

    public UserGenresDTO getUserGenres(int uid) {
        User user = getUserById(uid);
        UserGenresDTO dto = modelMapper.map(user, UserGenresDTO.class);
        dto.setLikedGenres(user.getLikedGenres().stream().map(ge -> modelMapper.map(ge, GenreWithoutBooksDTO.class)).collect(Collectors.toList()));
        return dto;
    }

    public int getAllReviews(int uid) {
        User user = getUserById(uid);
        return user.getWrittenReviews().size();
    }

    public double getAvrRate(int id) throws SQLException {
        return userDAO.getAvrRate(id);
    }

    public int getTotalRate(int id) {
        return userDAO.getReviewTotalRate(id);
    }

    @SneakyThrows
    public int getSumRateReviews(int id) {
        return userDAO.getSumRate(id);
    }

    public UserWithoutRelationsDTO uploadPicture(MultipartFile file, int uid) {
        User user = getUserById(uid);
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String name = "uploads" + File.separator + "photos" + File.separator + System.nanoTime() + "." + ext;
        if (!validateFile(name)) {
            throw new BadRequestException("Choose proper file format!");
        }
        File f = new File(name);
        if (!f.exists()) {
            try {
                Files.copy(file.getInputStream(), f.toPath());
            } catch (IOException e) {
                throw new BadRequestException(e.getMessage());
            }
            if (user.getPhoto() != null) {
                File old = new File(user.getPhoto());
                old.delete();
            }
            user.setPhoto(name);
            userRepository.save(user);
            return modelMapper.map(user, UserWithoutRelationsDTO.class);
        }
        throw new BadRequestException("File exists!");
    }
}