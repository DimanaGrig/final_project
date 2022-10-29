package com.ittalents.goodreadsprojectv1.model.dao;

import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.model.entity.Genre;

import java.util.List;

public interface BookDAO {

    List<Book> findAllByAuthorName(String name);
    List<Book> findAllByGenre(Genre genre);
    List<Book> findAllByGenreAndAuthor(Genre genre, String name);
    List<Book> findTopHundredBooks ();
    List<Book> findRecentlyPopular();
    List<Book> findAllByOwner(String ownerName);
}
