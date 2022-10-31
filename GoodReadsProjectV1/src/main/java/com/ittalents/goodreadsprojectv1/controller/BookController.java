package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.*;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BookController extends AbstractController {

    @Autowired
    private BookService bookService;

    @PostMapping("/books")
    public ShowBookDTO uploadBook(@RequestBody UploadBookDTO uploadDto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return bookService.upload(uploadDto, id);
    }

    @PutMapping("/books/{isbn}")
    public ShowBookDTO editBook(@RequestBody EditBookDTO editDTO, HttpServletRequest request,
                                @PathVariable long isbn){
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
    @DeleteMapping("/books/{isbn}")
    public void deleteBook(HttpServletRequest request, @PathVariable long isbn) {
        int id = getLoggedUserId(request);
        checkLog(id);
        bookService.deleteBook(isbn, id);
    }

    @PostMapping("/books/{isbn}/cover")
    public ShowBookDTO uploadPicture(@RequestParam MultipartFile file,
                                     @PathVariable long isbn, HttpServletRequest request){
        int id = getLoggedUserId(request);
        checkLog(id);
        return bookService.uploadCover(file, isbn, id);
    }
}
