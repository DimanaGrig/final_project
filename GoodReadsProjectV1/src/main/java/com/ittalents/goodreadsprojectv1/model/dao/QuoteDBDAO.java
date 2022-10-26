package com.ittalents.goodreadsprojectv1.model.dao;

import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.model.entity.Quote;
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
public class QuoteDBDAO implements QuoteDAO{       // ---имаш многооо повторение на код //todo - fix query bodies
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Quote> findAllByBook(String name) {
        List<Quote> result=this.jdbcTemplate.query("----", new RowMapper<Quote>() {
            public Quote mapRow(ResultSet rs, int rowNum) throws SQLException {
                Quote quote=new Quote();
                quote.setContent(rs.getString("content"));
                return quote;
            }
        });
        throw  new NotFoundException("No quotes from this book!");
    }

    @Override
    public List<Quote> findAllByAuthor(String name) {
        List<Quote> result=this.jdbcTemplate.query("----", new RowMapper<Quote>() {
            public Quote mapRow(ResultSet rs, int rowNum) throws SQLException {
                Quote quote=new Quote();
                quote.setContent(rs.getString("content"));
                return quote;
            }
        });
        throw  new NotFoundException("No quotes by this author!");
    }

    @Override
    public List<Quote> findAllByBookAndAuthor(String title, String author) {
        List<Quote> result=this.jdbcTemplate.query("----", new RowMapper<Quote>() {
            public Quote mapRow(ResultSet rs, int rowNum) throws SQLException {
                Quote quote=new Quote();
                quote.setContent(rs.getString("content"));
                return quote;
            }
        });
        throw  new NotFoundException("No quotes from this book and by this author!");
    }
}
