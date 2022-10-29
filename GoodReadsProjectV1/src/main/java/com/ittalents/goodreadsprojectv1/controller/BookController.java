package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookDTO;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.EditBookDTO;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.ShowBookDTO;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.UploadBookDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Genre;
import com.ittalents.goodreadsprojectv1.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.List;

@RestController
public class BookController extends AbstractController {

    @Autowired
    private BookService bookService;

    @PostMapping("/books/upload")
    public ShowBookDTO uploadBook(@RequestBody UploadBookDTO uploadDto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return bookService.upload(uploadDto, id);
    }

    @PutMapping("/books/{isbn}/edit")
    public ShowBookDTO editBook(@RequestBody EditBookDTO editDTO, HttpServletRequest request, @PathVariable long isbn){
        int id = getLoggedUserId(request);
        checkLog(id);
        return bookService.editBook(editDTO,id, isbn);
    }

    @GetMapping("/books/{isbn}")
    public ShowBookDTO getByIsbn(@PathVariable long isbn, HttpServletRequest request){
        int id = getLoggedUserId(request);
        checkLog(id);
        return bookService.getByIsbn(isbn);
    }
    @Transactional
    @DeleteMapping("/books/{isbn}/delete")
    public void deleteBook(HttpServletRequest request, @PathVariable long isbn) {
        int id = getLoggedUserId(request);
        checkLog(id);
        bookService.deleteBook(isbn, id);
    }
}
