package com.ittalents.goodreadsprojectv1.model.dao;

import com.ittalents.goodreadsprojectv1.model.entity.Quote;

import java.util.List;

public interface QuoteDAO {
    List<Quote> findAllByBook(String name);
    List<Quote> findAllByAuthor(String name);
    List<Quote> findAllByBookAndAuthor(String title, String author);

    //check log - dali e lognat
    //get log - wzima[ id-ti za usera da si go znae[
}
