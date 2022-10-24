package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AuthorWithoutBooksDTO editAdditionalInf(AuthorWithoutBooksDTO editDTO, String newAdditionalInfo){
        if(validateSize(newAdditionalInfo)){
            editDTO.setInformationForAuthor(newAdditionalInfo);
            return editDTO;
        }
        throw new BadRequestException("Too many characters!");
    }
    public AuthorWithoutBooksDTO editWebsiteLink (AuthorWithoutBooksDTO editDTO, String newWebsiteLink){
        if(validateLink(newWebsiteLink)){
            editDTO.setAuthorWebsite(newWebsiteLink);
            return editDTO;
        }
        throw new BadRequestException("Not a valid URL!");
    }
    private boolean validateSize(String str) {
        if(str.length()>600){
            return false;
        }
        return true;
    }
    private boolean validateLink(String str){
        try {
            new URL(str).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return true;
    }
}
