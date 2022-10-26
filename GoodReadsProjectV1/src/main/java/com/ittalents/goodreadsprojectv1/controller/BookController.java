package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookDTO;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookWithoutQuotesDTO;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.ShowBookDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.model.entity.Genre;
import com.ittalents.goodreadsprojectv1.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class BookController extends AbstractController {

    @Autowired
    private BookService bookService;

                 //            -----------UPLOAD BOOK----------
    @PostMapping("/books/upload")
    public ShowBookDTO uploadBook(@RequestBody BookDTO uploadDto) {
        return bookService.upload(uploadDto);
    }

    @PutMapping("/books/{isbn}/edit")
    public ShowBookDTO editBook(@RequestBody BookDTO editDTO, long userId, String newContent, String newAddInfo, List<Genre> newSelectedGenres){
        return bookService.editBook(editDTO,userId, newContent, newAddInfo, newSelectedGenres);
    }

    @GetMapping("/books/{isbn}")
    public BookDTO getById(@PathVariable long isbn){
        return bookService.getByIsbn(isbn);
    }
}
