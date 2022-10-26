package com.ittalents.goodreadsprojectv1.model.dao;

import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.model.entity.Genre;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookDBDAO implements BookDAO {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    //todo, and also for the quotes

    @Override
    public List<Book> findAllByAuthorName(String name) {
        List<Book> result = this.jdbcTemplate.query("SELECT isbn, ---- " +
                "FROM books JOIN authors ON a.author_id=b.author_d HAVING a.///", new RowMapper<Book>() {
            // todo fix query;; махни * и замени с конкретни имена на колони
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book book = new Book();
                book.setIsbn(rs.getLong("isbn")); //check that the names in quotes are the same as table names;
                book.setName(rs.getString("name"));
                book.setContent(rs.getString("content"));
                book.setAdditionalInfo(rs.getString("additional_info"));
                return book;

                // това създава нов обект в хийпа - на мен не ми трябва;; ама то и ДТО-тата
                // са обекти в хийпа;; само дето тези трябва да са уникални, а ДТО-тата не би тр да е проблем
                //ти ги вадиш от базата
            }
        });
//        // https://mkyong.com/spring/spring-jdbctemplate-querying-examples/
//        Collection actors = this.jdbcTemplate.query(
//                "select first_name, surname from t_actor",
//                new RowMapper() {
        //  https://docs.spring.io/spring-framework/docs/3.0.x/spring-framework-reference/html/jdbc.html
//
//                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//                        Actor actor = new Actor();
//                        actor.setFirstName(rs.getString("first_name"));
//                        actor.setSurname(rs.getString("surname"));
//                        return actor;
//                    }}
        throw new NotFoundException("There is no book with this author name!");
    }

    @Override
    public List<Book> findAllByGenre(Genre genres) {
        List<Book> result = this.jdbcTemplate.query("SELECT isbn, ---- " +
                "FROM books JOIN authors ON a.author_id=b.author_d HAVING a.///", new RowMapper<Book>() {
            // todo fix query;; махни * и замени с конкретни имена на колони
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book book = new Book();
                book.setIsbn(rs.getLong("isbn")); //check that the names in quotes are the same as table names;
                book.setName(rs.getString("name"));
                book.setContent(rs.getString("content"));
                book.setAdditionalInfo(rs.getString("additional_info"));
                return book;
            }
        });
        throw new NotFoundException("There is no book with this genre!");
    }

    @Override
    public List<Book> findAllByGenreAndAuthor(Genre genre, String name) {
        List<Book> result = this.jdbcTemplate.query("SELECT isbn, ---- " +
                "FROM books JOIN authors ON a.author_id=b.author_d HAVING a.///", new RowMapper<Book>() {
            // todo fix query;; махни * и замени с конкретни имена на колони
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book book = new Book();
                book.setIsbn(rs.getLong("isbn")); //check that the names in quotes are the same as table names;
                book.setName(rs.getString("name"));
                book.setContent(rs.getString("content"));
                book.setAdditionalInfo(rs.getString("additional_info"));
                return book;
            }
        });
        throw new NotFoundException("There is no book with this author and genre!");
    }

    @Override
    public List<Book> findTopHundredBooks() {
        List<Book> result = this.jdbcTemplate.query("SELECT isbn, ---- " +
                "FROM books JOIN authors ON a.author_id=b.author_d HAVING a.///", new RowMapper<Book>() {
            // todo fix query;; махни * и замени с конкретни имена на колони
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book book = new Book();
                book.setIsbn(rs.getLong("isbn")); //check that the names in quotes are the same as table names;
                book.setName(rs.getString("name"));
                book.setContent(rs.getString("content"));
                book.setAdditionalInfo(rs.getString("additional_info"));
                return book;
            }
        });
        throw new NotFoundException("No books in list!");
    }

    @Override
    public List<Book> findRecentlyPopular() {
        List<Book> result = this.jdbcTemplate.query("SELECT isbn, ---- " +
                "FROM books JOIN authors ON a.author_id=b.author_d HAVING a.///", new RowMapper<Book>() {
            // todo fix query;; махни * и замени с конкретни имена на колони
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book book = new Book();
                book.setIsbn(rs.getLong("isbn")); //check that the names in quotes are the same as table names;
                book.setName(rs.getString("name"));
                book.setContent(rs.getString("content"));
                book.setAdditionalInfo(rs.getString("additional_info"));
                return book;
            }
        });
        throw new NotFoundException("No books in list!");
    }

    @Override
    public List<Book> findAllByOwner(String ownerName) {
        List<Book> result = this.jdbcTemplate.query("-----", new RowMapper<Book>() {
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book book = new Book();
                book.setIsbn(rs.getLong("isbn")); //check that the names in quotes are the same as table names;
                book.setName(rs.getString("name"));
                book.setContent(rs.getString("content"));
                book.setAdditionalInfo(rs.getString("additional_info"));
                return book;
            }
        });
        throw new NotFoundException("This owner has no books!");
    }
}
