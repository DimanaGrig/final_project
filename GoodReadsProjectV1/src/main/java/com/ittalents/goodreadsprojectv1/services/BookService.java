package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.dao.BookDao;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.*;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.entity.*;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import com.ittalents.goodreadsprojectv1.model.repository.BookRepository;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService extends  AbstractService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BookDao bookDao;

    @Transactional
    public ShowBookDTO upload(UploadBookDTO uploadDto, long id) {
        if (id != ADMIN_ID) {
            throw new UnauthorizedException("You can't upload books!");
        }
        if (uploadDto.getAuthorId() == 0) {
            throw new BadRequestException("You have to first create the author!");
        }
        if (!validateISBN(uploadDto.getIsbn())) {
            throw new BadRequestException("Invalid ISBN!");
        }
        if (bookRepository.existsByIsbn(uploadDto.getIsbn())) {
            throw new BadRequestException("Book has already been uploaded!");
        }
        if (!validateName(uploadDto.getName())) {
            throw new BadRequestException("Invalid title!");
        }
        if ((!validateSize(uploadDto.getAdditionalInfo()))) {
            throw new BadRequestException("Too many characters!");
        }
        if ((!validateSize(uploadDto.getContent()))) {
            throw new BadRequestException("Too many characters!");
        }
        if(!validateGenres(uploadDto.getGenres())){
            throw new BadRequestException("Invalid input for genres");
        }
        Book book = modelMapper.map(uploadDto, Book.class);
        book.setAuthor(getAuthorById(uploadDto.getAuthorId()));
        book.setBookGenres(createListForGenres(uploadDto.getGenres()));
        bookRepository.save(book);
        ShowBookDTO result = modelMapper.map(book, ShowBookDTO.class);
        result.setGenres(createListForGenreDTO(uploadDto.getGenres()));
        return result;
    }

    public ShowBookDTO editBook(EditBookDTO dto, int uid) {
        Book book = getBookByISBN(dto.getIsbn());
        if (uid != ADMIN_ID) {
            throw new UnauthorizedException("You can't edit this book!");
        }
        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new NotFoundException("Book does not exist!");
        }
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
        bookRepository.save(book);
        return modelMapper.map(book, ShowBookDTO.class);
    }

    public void deleteBook(long isbn, int uid) {
        if (uid == ADMIN_ID) {
            bookRepository.deleteByIsbn(isbn);
            System.out.println("Book with isbn = " + isbn + " has been deleted!");
            return;
        }
        throw new UnauthorizedException("You can't delete books!");
    }

    public ShowBookDTO uploadCover(MultipartFile file, long isbn, int id){
        if(id==ADMIN_ID){
            Book b=getBookByISBN(isbn);
            String ext= FilenameUtils.getExtension(file.getOriginalFilename());
            String name="uploads"+File.separator+"covers"+ File.separator+System.nanoTime()+"."+ext;
            File f=new File(name);
            if(!f.exists()){
                try {
                    Files.copy(file.getInputStream(), f.toPath());
                } catch (IOException e) {
                    throw new BadRequestException(e.getMessage());
                }
                if(b.getBookCover()!=null){
                    File old=new File(b.getBookCover());
                    old.delete();
                }
                b.setBookCover(name);
                bookRepository.save(b);
                return modelMapper.map(b, ShowBookDTO.class);
            }
            throw new BadRequestException("File exists!");
        }
        throw new UnauthorizedException("You can't upload pictures!");
    }

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

    public boolean validateGenres(List<Integer> genres){
        if(genres.isEmpty()){
            throw new BadRequestException("You have to select genres!");
        }
        Set<Integer> genreSet=genres.stream().collect(Collectors.toSet());
        if(genreSet.size()!=genres.size()){
            return false;
        }
        return true;
    }

    public List<Genre> createListForGenres(List<Integer> genresIds){
        List<Genre> genres=new ArrayList<>();
        for (Integer i:genresIds) {
            genres.add(getGenreById(i));
        }
        return genres;
    }

    public List<GenreWithoutBooksDTO> createListForGenreDTO(List<Integer> genresIds){
        List<GenreWithoutBooksDTO> genresWithoutBooks=new ArrayList<>();
        for (Integer i:genresIds) {
            GenreWithoutBooksDTO g=new GenreWithoutBooksDTO();
            g.setId(i);
            g.setName(getGenreById(i).getName());
            genresWithoutBooks.add(g);
        }
        return genresWithoutBooks;
    }

    public ShowBookDTO getByIsbn(long isbn) {
        Book book = getBookByISBN(isbn);
        ShowBookDTO dto = modelMapper.map(book, ShowBookDTO.class);
        return dto;
    }

    public List<ShowBookDTO> getRecommendations(int userId) {
        User user = getUserById(userId);
        List<Genre> favGenres = user.getLikedGenres();
        Set<Book> userBooks = new HashSet<>();
        List<Author> favAuthors = new ArrayList<>();
        for (Shelf s : user.getUserShelves()) {
            for (Book b : s.getBooksAtThisShelf()) {
                userBooks.add(b);
                Author a = b.getAuthor();
                favAuthors.add(a);
            }
        }
        for (Genre g : favGenres) {
            for (Book b : g.getBooksInGenre()) {
                userBooks.add(b);
            }
        }
        for (Author a : favAuthors) {
            for (Book b : a.getAllBooks()) {
                userBooks.add(b);
            }
        }
        List<Book> booksToRec = new ArrayList<>();
        booksToRec.addAll(userBooks);
        if(booksToRec.isEmpty()){
            throw new BadRequestException("Nothing to recommend for now");
        }
        return booksToRec.stream().
                map(b -> modelMapper.map(b, ShowBookDTO.class)).
                collect(Collectors.toList());
    }
    public List<BookWithoutRelationsDTO> getBooksByKeyword(String str) throws SQLException {
        List<Book> list=bookDao.getBooksByKeyword(str);
        List<BookWithoutRelationsDTO> result= list.stream().
                map(b -> modelMapper.map(b, BookWithoutRelationsDTO.class)).
                collect(Collectors.toList());
        if(result.isEmpty()){
            throw new BadRequestException("No such author!");
        }
        return result;
    }

    public List<BookWithoutRelationsDTO> getBooksByKeywordInTitle(String str) throws SQLException {
        List<Book> list=bookDao.getBooksByKeywordInTitle(str);
        List<BookWithoutRelationsDTO> result =list.stream().
                map(b -> modelMapper.map(b, BookWithoutRelationsDTO.class)).
                collect(Collectors.toList());
        if(result.isEmpty()){
            throw new BadRequestException("No such author!");
        }
        return result;
    }

    public List<BookHomePageDTO> getHomePage() {
        List<BookHelpDTO> books= bookDao.getHomePage();
        List<BookHomePageDTO> result =books.stream().
                map(b -> modelMapper.map(b, BookHomePageDTO.class)).
                collect(Collectors.toList());
        if(result.isEmpty()){
            throw new  BadRequestException("No rate books!");
        }
        return result;
        }


}
