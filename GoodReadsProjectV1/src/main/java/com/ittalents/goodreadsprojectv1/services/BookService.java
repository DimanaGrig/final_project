package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.UploadPictureDTO;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.*;
import com.ittalents.goodreadsprojectv1.model.entity.Author;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import com.ittalents.goodreadsprojectv1.model.repository.BookRepository;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService extends  AbstractService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public ShowBookDTO upload(UploadBookDTO uploadDto, long id) {
        if (id == ADMIN_ID) {
            if (uploadDto.getAuthorId() != 0) {
                if (validateISBN(uploadDto.getIsbn())) {
                    if (!bookRepository.existsByIsbn(uploadDto.getIsbn())) {
                        if (!validateName(uploadDto.getName())) {
                            throw new BadRequestException("Invalid title!");
                        }
                        if ((!validateSize(uploadDto.getAdditionalInfo())
                                /*&& (uploadDto.getAdditionalInfo()!=null)*/)) {
                            throw new BadRequestException("Too many characters!");
                        }
                        if ((!validateSize(uploadDto.getContent())
                        /*&&  (uploadDto.getContent()!=null)*/)) {
                            throw new BadRequestException("Too many characters!");
                        }
                        Book book = modelMapper.map(uploadDto, Book.class);
                        book.setAuthor(getAuthorById(uploadDto.getAuthorId()));
                        bookRepository.save(book);
                        ShowBookDTO result = modelMapper.map(book, ShowBookDTO.class);
                        return result;
                    }
                    throw new BadRequestException("Book has already been uploaded!");
                }
                throw new BadRequestException("Invalid ISBN!");
            }
            throw new BadRequestException("You have to first create the author!");
        }
        throw new UnauthorizedException("You can't upload books!");
    }

    //                  ----------EDIT BOOK-------

    public ShowBookDTO editBook(EditBookDTO dto, int uid, long isbn) {
        Book book = getBookByISBN(isbn);
        if (uid == ADMIN_ID) {
            if (bookRepository.existsByIsbn(isbn)) {
                if (validateSize(dto.getAdditionalInfo())) {
                    book.setAdditionalInfo(dto.getAdditionalInfo());
                } else {
                    throw new BadRequestException("Too many characters in additional information!");
                }
                if (validateSize(dto.getContent())) {
                    book.setContent(dto.getContent());
                } else {
                    throw new BadRequestException("Too many characters in content!");
                }
                //  if () //genres

                bookRepository.save(book);
                return modelMapper.map(book, ShowBookDTO.class);
            }
            throw new NotFoundException("Book does not exist!");
        }
        throw new UnauthorizedException("You can't edit this book!");
    }

    //              ---------------DELETE-------------
    public void deleteBook(long isbn, int uid) {
        if (uid == ADMIN_ID) {
            bookRepository.deleteByIsbn(isbn);
            System.out.println("Book with isbn = " + isbn + " have been deleted!");
            return;
        }
        throw new UnauthorizedException("Yo can't delete books!");
    }

    //                        ----------Cover------------
    public ShowBookDTO uploadCover(MultipartFile fl, long isbn, int id){
        if(id==ADMIN_ID){
            Book b=getBookByISBN(isbn);  //todo delete DTOs for pic upload
            String ext= FilenameUtils.getExtension(fl.getOriginalFilename());
            String name="uploads"+ File.separator+System.nanoTime()+File.separator+"."+ext;
            File f=new File(name);
            if(!f.exists()){
                try {
                    Files.copy(fl.getInputStream(), f.toPath());
                } catch (IOException e) {
                    throw new BadRequestException(e.getMessage());
                }
                b.setBookCover(name);
                bookRepository.save(b);
                return modelMapper.map(b, ShowBookDTO.class);
            }
            throw new BadRequestException("File exists!");
        }
        throw new UnauthorizedException("You can't upload pictures!");
    }

    //                              ----------VALIDATION METHODS-----------
    public boolean validateISBN(long isbn) {
        String strNumber = String.valueOf(isbn);
        int sum = 0;
        int i, t, intNumber, dNumber;

        if (strNumber.length() != 10) {
            return false;
        }
        for (i = 0; i < strNumber.length(); i++) {
            intNumber = Integer.parseInt(strNumber.substring(i, i + 1));
            dNumber = i + 1;
            t = dNumber * intNumber;
            sum = sum + t;
        }
        if ((sum % 11) == 0) {
            return true;
        }
        return false;
    }

    public ShowBookDTO getByIsbn(long isbn) {
        Book book = getBookByISBN(isbn);
        ShowBookDTO dto = modelMapper.map(book, ShowBookDTO.class);
        return dto;
    }
}
