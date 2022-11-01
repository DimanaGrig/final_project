package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithNameDTO;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.*;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Author;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public ShowBookDTO editBook(@RequestBody EditBookDTO editDTO, HttpServletRequest request){
        int id = getLoggedUserId(request);
        checkLog(id);
        return bookService.editBook(editDTO,id);
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

    @GetMapping("/books/search/{str}")
    public List<ShowBookDTO> getBooksByTitle(HttpServletRequest request, @PathVariable String str) {
        int id = getLoggedUserId(request);
        checkLog(id);
        List<ShowBookDTO> allBooksDto = bookService.getBooksByTitle(str);
        return allBooksDto;
    }

    @GetMapping("/books/find/{str}")
    public List<ShowBookDTO> getBooksByTitleAndAuthorKeyword(HttpServletRequest request, @PathVariable String str) {
        int id = getLoggedUserId(request);
        checkLog(id);
        List<ShowBookDTO> allBooksDto = bookService.getBooksByTitleAndAuthorKeyword(str);
        return allBooksDto;
    }

    @GetMapping("books/recommended")      //todo ne e testvano
    public List<ShowBookDTO> getRecommendations( HttpServletRequest request){
        int id = getLoggedUserId(request);
        checkLog(id);
        List<ShowBookDTO> allBooksDto = bookService.getRecommendations(id);
        return allBooksDto;
    }
}
