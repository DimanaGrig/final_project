package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Author;
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

    public AuthorWithoutBooksDTO editProfile(AuthorWithoutBooksDTO editDTO,String newWebsiteLink, String newAdditionalInfo){

        Author a=modelMapper.map(editDTO, Author.class); //todo - наистина нз дали е нужно това допълнително мапване
        if(validateSize(newAdditionalInfo)){
            editDTO.setInformationForAuthor(newAdditionalInfo);
            a.setInformationForAuthor(newAdditionalInfo);
        }
        else {
            throw new BadRequestException("Too many characters!");
        }
        if(validateLink(newWebsiteLink)){
            editDTO.setAuthorWebsite(newWebsiteLink);
            a.setAuthorWebsite(newWebsiteLink);
        }
        else{
            throw new BadRequestException("Not a valid URL!");
        }
        return editDTO;
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
