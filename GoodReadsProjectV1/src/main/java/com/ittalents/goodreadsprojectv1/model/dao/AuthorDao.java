package com.ittalents.goodreadsprojectv1.model.dao;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutRelationsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorDao {
    private static final String GET_AUTHORS_BY_KEYWORD = "SELECT a.id, a.first_name, a.last_name  " +
            "FROM authors a WHERE a.first_name LIKE ? OR a.last_name LIKE ?;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AuthorWithoutRelationsDTO> getAuthorsByKeyword(String str) throws SQLException {
        List<AuthorWithoutRelationsDTO> authors=new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_AUTHORS_BY_KEYWORD)) {
            ps.setString(1, "%" + str + "%");
            ps.setString(2,"%"+str+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AuthorWithoutRelationsDTO dto=new AuthorWithoutRelationsDTO();
                dto.setId(rs.getInt("id"));
                dto.setFirstName(rs.getString("first_name"));
                dto.setLastName(rs.getString("last_name"));

                authors.add(dto);
            }
            rs.close();
        }
        return authors;
    }
}





