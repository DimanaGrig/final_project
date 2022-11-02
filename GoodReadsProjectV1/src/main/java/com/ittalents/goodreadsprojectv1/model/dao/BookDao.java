package com.ittalents.goodreadsprojectv1.model.dao;

import com.ittalents.goodreadsprojectv1.model.entity.Author;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.repository.AuthorRepository;
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
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Book> getBooksByKeyword(String str) throws SQLException {
        List<Book> bookList=new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BOOKS_BY_KEYWORD_IN_TITLE_OR_AUTHOR)) {
            ps.setString(1, "%" + str + "%");
            ps.setString(2,"%"+str+"%");
            ps.setString(3,"%"+str+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book=new Book();
                book.setIsbn(rs.getLong("isbn"));
                book.setName(rs.getString("name"));
                book.setBookCover(rs.getString("book_cover"));
                Author a=authorRepository.findAuthorById(rs.getInt("author_id")).
                        orElseThrow(()->new NotFoundException("Error"));
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
        List<Book> bookList=new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BOOKS_BY_KEYWORD_IN_TITLE)) {
            ps.setString(1, "%" + str + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book=new Book();
                book.setIsbn(rs.getLong("isbn"));
                book.setName(rs.getString("name"));
                book.setBookCover(rs.getString("book_cover"));
                Author a=authorRepository.findAuthorById(rs.getInt("author_id")).
                        orElseThrow(()->new NotFoundException("Error"));
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
}
