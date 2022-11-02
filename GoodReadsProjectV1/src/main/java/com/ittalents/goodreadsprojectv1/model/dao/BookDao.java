package com.ittalents.goodreadsprojectv1.model.dao;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookHelpDTO;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookHomePageDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Author;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.repository.AuthorRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookDao {
    @Autowired
    private AuthorRepository authorRepository;
    private static final String GET_BOOKS_BY_KEYWORD_IN_TITLE_OR_AUTHOR = "SELECT b.ISBN, b.name, b.book_cover," +
            " b.author_id," +
            "a.id, a.first_name, a.last_name\n" +
            "FROM books b JOIN authors a ON b.author_id=a.id\n" +
            "WHERE  a.first_name LIKE ? OR a.last_name LIKE ? OR b.name LIKE ?;";
    private static final String GET_BOOKS_BY_KEYWORD_IN_TITLE = "SELECT b.ISBN, b.name, b.book_cover," +
            " b.author_id," +
            "a.id, a.first_name, a.last_name\n" +
            "FROM books b JOIN authors a ON b.author_id=a.id\n" +
            "WHERE  b.name LIKE ?;";

    private static final String GET_HIGHEST_RATE_BOOKS = "SELECT b.ISBN,b.name,SUM(rate)/COUNT(rate) AS avr_rate,b.book_cover," +
            "a.id AS author_id,a.first_name,a.last_name " +
            "FROM books b  JOIN reviews r ON(b.ISBN=r.book_id) " +
            "JOIN authors a ON (b.author_id=a.id)";

    private static final String GET_HIGHEST_RATE_BOOKS_ORDERED = "SELECT b.ISBN,b.name,SUM(rate)/COUNT(rate) AS avr_rate,b.book_cover,a.id " +
            "AS author_id,a.first_name,a.last_name FROM books b  " +
            "JOIN reviews r ON(b.ISBN=r.book_id) JOIN authors a ON (b.author_id=a.id)" +
            "GROUP BY r.book_id ORDER BY avr_rate DESC;";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Book> getBooksByKeyword(String str) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BOOKS_BY_KEYWORD_IN_TITLE_OR_AUTHOR)) {
            ps.setString(1, "%" + str + "%");
            ps.setString(2, "%" + str + "%");
            ps.setString(3, "%" + str + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setIsbn(rs.getLong("isbn"));
                book.setName(rs.getString("name"));
                book.setBookCover(rs.getString("book_cover"));
                Author a = authorRepository.findAuthorById(rs.getInt("author_id")).
                        orElseThrow(() -> new NotFoundException("Error"));
                book.setAuthor(a);
                a.setId(rs.getInt("author_id"));
                a.setFirstName(rs.getString("first_name"));
                a.setLastName(rs.getString("last_name"));
                bookList.add(book);
            }
            rs.close();
        }
        return bookList;
    }

    public List<Book> getBooksByKeywordInTitle(String str) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BOOKS_BY_KEYWORD_IN_TITLE)) {
            ps.setString(1, "%" + str + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setIsbn(rs.getLong("isbn"));
                book.setName(rs.getString("name"));
                book.setBookCover(rs.getString("book_cover"));
                Author a = authorRepository.findAuthorById(rs.getInt("author_id")).
                        orElseThrow(() -> new NotFoundException("Error"));
                book.setAuthor(a);
                a.setId(rs.getInt("author_id"));
                a.setFirstName(rs.getString("first_name"));
                a.setLastName(rs.getString("last_name"));
                bookList.add(book);
            }
            rs.close();
        }
        return bookList;
    }

    @SneakyThrows
    public List<BookHelpDTO> getHomePage() {
        List<BookHelpDTO> books = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement state = connection.prepareStatement(GET_HIGHEST_RATE_BOOKS_ORDERED)) {
            ResultSet resultSet = state.executeQuery();
            while (resultSet.next()) {
                BookHelpDTO book = new BookHelpDTO();
                book.setIsbn(resultSet.getLong("ISBN"));
                book.setName(resultSet.getString("name"));
                book.setAvrRate(resultSet.getDouble("avr_rate"));
                book.setBookCover(resultSet.getString("book_cover"));
                Author a = authorRepository.findAuthorById(resultSet.getInt("author_id")).
                        orElseThrow(() -> new NotFoundException("Error"));
                book.setAuthor(a);
                a.setId(resultSet.getInt("author_id"));
                a.setFirstName(resultSet.getString("first_name"));
                a.setLastName("last_name");
                books.add(book);
            }
            resultSet.close();
        }
        return books;
    }
}
