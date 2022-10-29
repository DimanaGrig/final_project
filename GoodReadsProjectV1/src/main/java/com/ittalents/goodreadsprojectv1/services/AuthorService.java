package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.EditAuthorDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Author;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import com.ittalents.goodreadsprojectv1.model.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class AuthorService extends AbstractService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AuthorWithoutBooksDTO editAuthor(EditAuthorDTO editDTO, int uid, int id){
        if(uid==ADMIN_ID) {
             Author author= findAuthorById(id);
            // todo picture
            if(validateSize(editDTO.getInformationForAuthor())){
               author.setInformationForAuthor(editDTO.getInformationForAuthor());
            } else {
               throw new BadRequestException("Too many characters!");
            }
            if(validateLink(editDTO.getAuthorWebsite()) || editDTO.getAuthorWebsite()==null){
                editDTO.setAuthorWebsite(editDTO.getAuthorWebsite());
            } else{
                throw new BadRequestException("Not a valid URL!");
            }
            // todo authorRep.save(author) ???
            AuthorWithoutBooksDTO result=modelMapper.map(author, AuthorWithoutBooksDTO.class);
            return result;
        }
        throw new UnauthorizedException("You can't edit information about author!");
    }

    public AuthorWithoutBooksDTO createAuthor(AuthorWithoutBooksDTO dto, int uid) {
        if(uid==ADMIN_ID) {
            Author author=new Author();
            if(!validateName(dto.getFirstName()) || !validateName(dto.getLastName())){
                throw new BadRequestException("Invalid name!");
            }
            //todo picture

            if(!validateSize(dto.getInformationForAuthor())){
                throw new BadRequestException("Too many characters!");
            }
            if(!validateLink(dto.getAuthorWebsite()) && dto.getAuthorWebsite()!=null){
                throw new BadRequestException("Invalid link!");
            }
            author=modelMapper.map(dto, Author.class);
            authorRepository.save(author);
            AuthorWithoutBooksDTO result = modelMapper.map(author, AuthorWithoutBooksDTO.class);
            return result;
        }
        throw new UnauthorizedException("You can't upload books!");
    }
    public void deleteAuthor(int aid, int uid) {
        if(uid==ADMIN_ID){
            authorRepository.deleteById(aid);
            System.out.println("Author with id = " + aid + " have been deleted!");
            return;
        }
        throw new UnauthorizedException("You can't delete books!");
    }
    public boolean validateLink(String str){
        try {
            new URL(str).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return true;
    }
    public Author getById(int aid){
        return authorRepository.findAuthorById(aid).orElseThrow(()->new NotFoundException("Author not found!"));
    }
}
