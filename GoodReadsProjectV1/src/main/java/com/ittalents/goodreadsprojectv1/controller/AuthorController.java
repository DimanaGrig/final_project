package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.EditAuthorDTO;
import com.ittalents.goodreadsprojectv1.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@RestController
public class AuthorController extends AbstractController {
    @Autowired
    private AuthorService authorService;

    @PostMapping("/authors/{id}/pic")
    public AuthorWithoutBooksDTO uploadPicture(@RequestParam MultipartFile file, @PathVariable int id,
                                               HttpServletRequest request){
        int uid = getLoggedUserId(request);
        checkLog(uid);
        return authorService.uploadPicture(file,id, uid);
    }

    @PostMapping("/authors")
    public AuthorWithoutBooksDTO createAuthor(@RequestBody AuthorWithoutBooksDTO dto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return authorService.createAuthor(dto, id);
    }

    @PutMapping("/authors")
    public AuthorWithoutBooksDTO editAuthor(@RequestBody EditAuthorDTO editDTO, HttpServletRequest request) {
        int uid = getLoggedUserId(request);
        checkLog(uid);
        return authorService.editAuthor(editDTO, uid);
    }

    @Transactional
    @DeleteMapping("/authors/{id}")
    public void deleteAuthor(HttpServletRequest request, @PathVariable int id) {
        int uid = getLoggedUserId(request);
        checkLog(uid);
        authorService.deleteAuthor(id, uid);
    }

    @GetMapping("/authors/{id}")
    public AuthorWithoutBooksDTO getById(@PathVariable int id, HttpServletRequest request){
        int uid = getLoggedUserId(request);
        checkLog(uid);
        return authorService.getById(id);
    }

    @GetMapping("authors/search/{str}")
    public List<AuthorWithoutRelationsDTO> getAuthorsByKeyword(HttpServletRequest request,
                                                               @PathVariable String str) throws SQLException {
        int id = getLoggedUserId(request);
        checkLog(id);
        List<AuthorWithoutRelationsDTO> allAuthorsDto =  authorService.getAuthorsByKeyword(str);
        return allAuthorsDto;
    }
}
