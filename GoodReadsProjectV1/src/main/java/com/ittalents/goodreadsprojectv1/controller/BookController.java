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

    @PutMapping("/books/{isbn}/editContent")
    public ShowBookDTO editContent(@RequestBody BookDTO editDTO, String newContent){
        return bookService.editContent(editDTO, newContent);
    }
    @PutMapping("/books/{isbn}/editAdditionalInfo")
    public ShowBookDTO editAdditionalInfo(@RequestBody BookDTO editDTO, String newAdditionalInfo){
        return bookService.editAdditionalInfo(editDTO, newAdditionalInfo);
    }
    @PutMapping("/books/{isbn}/selectGenres")
    public BookWithoutQuotesDTO selectGenres(@RequestBody BookDTO editDTO, Set<Genre> selectedGenres){
        return bookService.selectGenres(editDTO, selectedGenres);
    }

    //  public UserRespWithoutPassDTO register(@RequestBody UserRequRegisterDTO dto) {
    //        return userService.register(dto);
    //    }
    //
    //    @PostMapping("/auth")
    //    public UserRespWithoutPassDTO login(@RequestBody UserRequLoginDTO dto, HttpServletRequest request) {
    //        if (getLoggedUserId(request) > 0) {
    //            throw new BadRequestException("You already have logged!");
    //        }
    //        UserRespWithoutPassDTO response = userService.login(dto);
    //        if (response != null) {
    //            logUser(request, response.getId());
    //            return response;
    //        } else {
    //            throw new BadRequestException("Wrong credentials!");
    //        }
    ////        String pass = u.getPass();
    ////        UserController.checkPassword(pass);
    //        u.setLastEnter(LocalDateTime.now());
    //        u.setMemberFrom(LocalDateTime.now());
    //        userRepository.save(u);
    //        return u;
    //    }
    //
    //
    //    @PostMapping("/logout")
    //    public void logout(HttpSession session) {
    //        session.invalidate();
    //    };*/
    @GetMapping("/books")
       public List<BookDTO> getAllBooks() {
        List<BookDTO> allBooksDTO = bookService.getAllBooks();
        return allBooksDTO;
    }
    @GetMapping("/books/{isbn}")
    public BookDTO getById(@PathVariable long isbn){
        return bookService.getByIsbn(isbn);
    }
}
