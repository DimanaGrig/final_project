package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.EditAuthorDTO;
import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.UploadPictureDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Author;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import com.ittalents.goodreadsprojectv1.model.repository.AuthorRepository;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;


@Service
public class AuthorService extends AbstractService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AuthorWithoutBooksDTO editAuthor(EditAuthorDTO editDTO, int uid, int id){
        if(uid==ADMIN_ID) {
             Author author= findAuthorById(id);
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
            authorRepository.save(author);
            AuthorWithoutBooksDTO result=modelMapper.map(author, AuthorWithoutBooksDTO.class);
            return result;
        }
        throw new UnauthorizedException("You can't edit information about author!");
    }
    public AuthorWithoutBooksDTO uploadPicture(MultipartFile fl, int id, int uid){
        if(uid==ADMIN_ID){
            Author a=findAuthorById(id);
            String ext= FilenameUtils.getExtension(fl.getOriginalFilename());
            String name="uploads"+File.separator+System.nanoTime()+File.separator+"."+ext;
            File f=new File(name);
            if(!f.exists()){
                try {
                    Files.copy(fl.getInputStream(), f.toPath());
                } catch (IOException e) {
                    throw new BadRequestException(e.getMessage());
                }

                a.setPicture(name);
                authorRepository.save(a);
                return modelMapper.map(a, AuthorWithoutBooksDTO.class);
            }
            throw new BadRequestException("File exists!");
          }
        throw new UnauthorizedException("You can't upload pictures!");
        }

    public AuthorWithoutBooksDTO createAuthor(AuthorWithoutBooksDTO dto, int uid) {
        if(uid==ADMIN_ID) {
            Author author;
            if(!validateName(dto.getFirstName()) || !validateName(dto.getLastName())){
                throw new BadRequestException("Invalid name!");
            }
            if(!validateSize(dto.getInformationForAuthor()) /*&& dto.getInformationForAuthor()!=null*/){
                throw new BadRequestException("Too many characters!");
            }
            if(!validateLink(dto.getAuthorWebsite()) /*&& dto.getAuthorWebsite()!=null*/){
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
        return findAuthorById(aid);
    }
}
