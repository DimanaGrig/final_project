package com.ittalents.goodreadsprojectv1.model.dao;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@Component
public class UserDAO {
    private static final String GET_AVR_RATE_REVIEWS = "SELECT SUM(r.rate)/COUNT(r.rate)" +
            " AS AVR_rate FROM reviews r GROUP BY r.user_id =?;";
    private static final String GET_REVIEW_TOTAL_RATE = "SELECT SUM(r.rate) AS RateSum " +
            "FROM reviews r WHERE r.user_id=?;";
    private static final String GET_SUM_REVIEWS = "SELECT COUNT(r.rate) AS reviews" +
            " FROM reviews r WHERE r.user_id =?;";

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

}