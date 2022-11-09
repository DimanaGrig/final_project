package com.ittalents.goodreadsprojectv1.model.dao;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookHelpDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserHelpDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Author;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.model.entity.Review;
import com.ittalents.goodreadsprojectv1.model.entity.User;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.repository.AuthorRepository;
import com.ittalents.goodreadsprojectv1.model.repository.BookRepository;
import com.ittalents.goodreadsprojectv1.model.repository.ReviewRepository;
import com.ittalents.goodreadsprojectv1.services.AbstractService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@Component
public class UserDAO {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;
    private static final String GET_AVR_RATE_REVIEWS = "SELECT SUM(r.rate)/COUNT(r.rate)" +
            " AS AVR_rate FROM reviews r GROUP BY r.user_id =?;";
    private static final String GET_REVIEW_TOTAL_RATE = "SELECT SUM(r.rate) AS RateSum " +
            "FROM reviews r WHERE r.user_id=?;";
    private static final String GET_SUM_REVIEWS = "SELECT COUNT(r.rate) AS reviews" +
            " FROM reviews r WHERE r.user_id =?;";

    private static final String GET_USER_FRIENDS = "SELECT l.user_id,u.first_name,u.last_name,u.addres,u.photo, " +
            "r.id AS review_id,MAX(r.rate) AS max_rate,r.opinion ,b.ISBN,b.name,a.id AS author_id, " +
            "a.first_name AS author_first_name,a.last_name AS author_last_name " +
            "FROM list_of_followers l " +
            "JOIN users u ON(l.user_id=u.id) " +
            "LEFT JOIN reviews r ON(u.id=r.user_id) " +
            "LEFT JOIN books b ON(r.book_id=b.ISBN) " +
            "LEFT JOIN authors a ON(b.author_id=a.id) " +
            "LEFT JOIN shelves s ON(l.user_id =s.user_id) " +
            "WHERE l.follower_id=? " +
            "GROUP BY r.user_id " +
            "ORDER BY max_rate DESC;";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @SneakyThrows
    public double getAvrRate(int id) {
        double result = 0.0;
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement state = connection.prepareStatement(GET_AVR_RATE_REVIEWS)) {
            state.setInt(1, id);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                result = rs.getDouble(1);
            }
            rs.close();
        }
        return result;
    }

    @SneakyThrows
    public int getReviewTotalRate(int id) {
        int result = 0;
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement state = connection.prepareStatement(GET_REVIEW_TOTAL_RATE)) {
            state.setInt(1, id);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
        }
        return result;
    }

    @SneakyThrows
    public int getSumRate(int id) {
        int result = 0;
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement state = connection.prepareStatement(GET_SUM_REVIEWS)) {
            state.setInt(1, id);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
        }
        return result;
    }
    @SneakyThrows
    public List<UserHelpDTO> getUserFriends(int uid) {
        List<UserHelpDTO> users = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement state = connection.prepareStatement(GET_USER_FRIENDS)) {
            state.setInt(1, uid);
            ResultSet resultSet = state.executeQuery();
            while (resultSet.next()) {
                UserHelpDTO friend = new UserHelpDTO();
                friend.setId(resultSet.getInt("user_id"));
                friend.setFirstName(resultSet.getString("first_name"));
                friend.setLastName(resultSet.getString("last_name"));
                friend.setPhoto(resultSet.getString("photo"));
                if (resultSet.getInt("review_id") != 0) {
                    Review review = reviewRepository.getReviewById(resultSet.getInt("review_id")).
                            orElseThrow(() -> new NotFoundException("This review doesn't exist."));
                    friend.setReview(review);
                    review.setId(resultSet.getInt("review_id"));
                    review.setRate(resultSet.getInt("max_rate"));
                    review.setOpinion(resultSet.getString("opinion"));
                    Book book = bookRepository.findByIsbn(resultSet.getLong("ISBN")).
                            orElseThrow(() -> new NotFoundException("This book doesn't exist"));
                    friend.setBook(book);
                    book.setIsbn(resultSet.getLong("ISBN"));
                    book.setName(resultSet.getString("name"));
                    Author author = authorRepository.findAuthorById(resultSet.getInt("author_id")).
                            orElseThrow(() -> new NotFoundException("This book doesn't exist"));
                    friend.setAuthor(author);
                    author.setId(resultSet.getInt("author_id"));
                    author.setFirstName(resultSet.getString("author_first_name"));
                    author.setLastName(resultSet.getString("author_last_name"));
                }
                users.add(friend);
            }
            resultSet.close();
        }
        return users;
    }
}