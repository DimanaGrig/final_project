package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.EmailDTO.EmailDTO;
import com.ittalents.goodreadsprojectv1.model.entity.*;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractService {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ShelfRepository shelfRepository;
    @Autowired
    protected GenreRepository genreRepository;
    @Autowired
    protected BookRepository bookRepository;
    @Autowired
    protected ReviewRepository reviewRepository;
    @Autowired
    protected CommentRepository commentRepository;
    @Autowired
    protected AuthorRepository authorRepository;
    @Autowired
    protected ModelMapper modelMapper;
    @Autowired
    private JavaMailSender emailSender;


    public static final double ADMIN_ID = 1;
    public static final String REGISTRATION_SUBJECT="Successful registration in Goodreads!";
    public static final String REGISTRATION_MESSAGE=",\n\n Welcome to Goodreads!";

    protected User getUserById(int uid) {
        return userRepository.findById(uid).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    protected Genre getGenreById(int id) {
        return genreRepository.findById(id).orElseThrow(() -> new NotFoundException("Genre not found!"));
    }

    protected Shelf getShelfById(int id) {
        return shelfRepository.findById(id).orElseThrow(() -> new NotFoundException("Shelf not found!"));
    }

    protected Book getBookByISBN(long isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            return bookRepository.findByIsbn(isbn).orElseThrow(() -> new NotFoundException("Book not found!"));
        }
        return null;
    }

    protected Shelf getShelfByName(String name) {
        return shelfRepository.getShelfByName(name).orElseThrow(() -> new NotFoundException("Shelf not exist!"));

    }

    protected Review getReviewById(int id) {
        return reviewRepository.getReviewById(id).orElseThrow(() -> new NotFoundException("This review not exist."));
    }

    protected Author findAuthorById(int aid) {
        return authorRepository.findAuthorById(aid).orElseThrow(() -> new BadRequestException("Author not found!"));
    }

    protected Author getAuthorById(int aid) {
        return authorRepository.findAuthorById(aid).orElseThrow(() -> new NotFoundException("Author not found!"));
    }

    protected Comment getCommentById(int id) {
        return commentRepository.getCommentById(id).orElseThrow(() -> new NotFoundException("This comment doesn't exist."));
    }

    protected User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    protected boolean validateName(String name) {
        return (validateLength(name.length()) && validateNoSpecialChars(name));
    }

    protected boolean validateNoSpecialChars(String string) {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        boolean b = m.find();
        if (b) {
            return false;
        }
        return true;
    }

    protected boolean validateLength(int length) {
        if (length <= 45) {
            return true;
        }
        return false;
    }

    protected boolean validateSize(String str) {
        if (str == null) {
            return true;
        }
        if (str.length() > 600) {
            return false;
        }
        return true;
    }

    public List<User> findAllByName(String name) {
        List<User> users = userRepository.findAllByFirstNameLike(name);
        if (users.size() == 0) {
            throw new NotFoundException("We don't have users with that name");
        }
        return users;
    }

    public boolean validateFile(String name) {
        Pattern pattern;
        pattern = Pattern.compile("([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)");
        Matcher matcher;
        matcher = pattern.matcher(name);
        return matcher.matches();
    }

    protected void checkForLength(String string) {
        if (!validateSize(string)) {
            throw new BadRequestException("The length of the text is too big!");
        }
    }
    protected void checkValidRate(int rate) {
        if (rate > 5 || rate < 1) {
            throw new BadRequestException("Wrong credential.");
        }
    }
    public void sendSimpleMessage(EmailDTO dto) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ittalentsfinalpr@gmail.com");
            message.setTo(dto.getTo());
            message.setSubject(dto.getSubject());
            message.setText(dto.getMessage());
            emailSender.send(message);
    }
}


