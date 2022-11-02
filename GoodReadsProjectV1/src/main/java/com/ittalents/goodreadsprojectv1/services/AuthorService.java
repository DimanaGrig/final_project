package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.dao.AuthorDao;
import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.EditAuthorDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Author;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
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
import java.sql.SQLException;
import java.util.List;


@Service
public class AuthorService extends AbstractService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthorDao authorDao;

    public AuthorWithoutBooksDTO editAuthor(EditAuthorDTO editDTO, int uid/*, int id*/){
        if(uid==ADMIN_ID) {
            Author author= findAuthorById(editDTO.getId());
            if(validateSize(editDTO.getInformationForAuthor())){
               author.setInformationForAuthor(editDTO.getInformationForAuthor());
            } else {
               throw new BadRequestException("Too many characters!");
            }
            if(validateLink(editDTO.getAuthorWebsite()) || editDTO.getAuthorWebsite()==null){
                author.setAuthorWebsite(editDTO.getAuthorWebsite());
            } else{
                throw new BadRequestException("Not a valid URL!");
            }
            authorRepository.save(author);
            AuthorWithoutBooksDTO result=modelMapper.map(author, AuthorWithoutBooksDTO.class);
            return result;
        }
        throw new UnauthorizedException("You can't edit information about author!");
    }

    public AuthorWithoutBooksDTO uploadPicture(MultipartFile file, int id, int uid){
        if(uid==ADMIN_ID){
            Author a=findAuthorById(id);
            String ext= FilenameUtils.getExtension(file.getOriginalFilename());
            String name="uploads"+File.separator+"pictures"+File.separator+System.nanoTime()+"."+ext;
            File f=new File(name);
            if(!f.exists()){
                try {
                    Files.copy(file.getInputStream(), f.toPath());
                } catch (IOException e) {
                    throw new BadRequestException(e.getMessage());
                }
                if(a.getPicture()!=null){
                    File old=new File(a.getPicture());
                    old.delete();
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

    public AuthorWithoutBooksDTO getById(int aid){
        return modelMapper.map(authorRepository.findAuthorById(aid), AuthorWithoutBooksDTO.class);
    }
    public List<AuthorWithoutRelationsDTO> getAuthorsByKeyword(String str) throws SQLException {
        List<AuthorWithoutRelationsDTO> result=authorDao.getAuthorsByKeyword(str);
        if(result.isEmpty()){
            throw new BadRequestException("No such author!");
        }
        return result;
    }
}
