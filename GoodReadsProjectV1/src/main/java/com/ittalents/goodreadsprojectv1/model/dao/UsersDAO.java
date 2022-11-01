package com.ittalents.goodreadsprojectv1.model.dao;

import com.ittalents.goodreadsprojectv1.model.dto.users.UserRespFriendDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsersDAO {

    private static final String GET_AVR_RATE_REVIEWS = "SELECT SUM(r.rate)/COUNT(r.rate) AS AVR_rate FROM reviews r GROUP BY r.user_id =?;";
    private static final String GET_REVIEW_TOTAL_RATE = "SELECT SUM(r.rate) AS RateSum FROM reviews r WHERE r.user_id=?;";
    private static final String GET_SUM_REVIEWS = "SELECT COUNT(r.rate) AS reviews FROM reviews r WHERE r.user_id =?;";
    private static final String GET_FRIENDS = "SELECT l.user_id,u.first_name,u.last_name,u.addres,\n" +
            "r.id AS review_id,r.rate,r.opinion ,b.ISBN,b.name,a.id,\n" +
            "a.first_name AS author_first_name,a.last_name AS author_last_name,\n" +
            " COUNT(bs.book_id) AS total_books ,COUNT(f.user_id) AS friends\n" +
            "FROM list_of_followers l \n" +
            "JOIN users u ON(l.user_id=u.id)\n" +
            "LEFT JOIN reviews r ON(u.id=r.user_id)\n" +
            "LEFT JOIN books b ON(r.book_id=b.ISBN)\n" +
            "LEFT JOIN authors a ON(b.author_id=a.id) \n" +
            "LEFT JOIN shelves s ON(l.user_id =s.user_id)\n" +
            "LEFT JOIN books_at_shelves bs ON(s.id=bs.shelf_id)\n" +
            "LEFT JOIN  list_of_followers f ON(l.user_id=f.follоwer_id)\n" +
            "WHERE l.follоwer_id=?" +
            "GROUP BY r.user_id;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public double getAvrRate(int id) throws SQLException {
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

    public int getReviewTotalRate(int id) throws SQLException {
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

    public int getSumRate(int id) throws SQLException {
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

    public List<UserRespFriendDTO> getFriends(int id) throws SQLException {
        List<UserRespFriendDTO> friends = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement state = connection.prepareStatement(GET_FRIENDS)) {
            state.setInt(1, id);
            ResultSet resultSet = state.executeQuery();
            while (resultSet.next()) {
                UserRespFriendDTO friendDTO = new UserRespFriendDTO();
                friendDTO.setId(resultSet.getInt("user_id"));
                friendDTO.setFirstName(resultSet.getString("first_name"));
                friendDTO.setLastName(resultSet.getString("last_name"));
                friendDTO.setAddress(resultSet.getString("addres"));
                friendDTO.getHighestRateReview().setId(resultSet.getInt("review_id"));
                friendDTO.getHighestRateReview().setRate(resultSet.getInt("rate"));
                friendDTO.getHighestRateReview().setOpinion(resultSet.getString("opinion"));
                friendDTO.getHighestRateReview().getBook().setId(Long.parseLong(String.valueOf(resultSet.getLong("ISBN"))));
                friendDTO.getHighestRateReview().getBook().setName(resultSet.getString("name"));
                friendDTO.getHighestRateReview().getBook().getAuthor().setId(resultSet.getInt("id"));
                friendDTO.getHighestRateReview().getBook().getAuthor().setFirstName(resultSet.getString("author_first_name"));
                friendDTO.getHighestRateReview().getBook().getAuthor().setLastName(resultSet.getString("author_last_name"));
                friendDTO.setTotalFriends(resultSet.getInt("friends"));
                friendDTO.setTotalBooks(resultSet.getInt("total_book"));
                friends.add(friendDTO);
            }
            resultSet.close();
        }
        return friends;
    }
}



