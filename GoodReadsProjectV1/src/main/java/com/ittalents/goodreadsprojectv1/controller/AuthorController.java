package com.ittalents.goodreadsprojectv1.controller;


import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController extends AbstractController {
    @Autowired
    private AuthorService authorService;

    @PutMapping("/authors/{id}/editAdditionalInfo")
    public AuthorWithoutBooksDTO editAdditionalInfo(@RequestBody AuthorWithoutBooksDTO editDTO, String newAdditionalInfo){
        return authorService.editAdditionalInf(editDTO, newAdditionalInfo);
    }
    @PutMapping("/authors/{id}/editWebsiteLink")
    public AuthorWithoutBooksDTO editWebsiteLink(@RequestBody AuthorWithoutBooksDTO editDTO, String newWebsiteLink){
        return authorService.editWebsiteLink(editDTO, newWebsiteLink);
    }
}
