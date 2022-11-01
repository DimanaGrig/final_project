package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithNameDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    @Transactional
    public ShowBookDTO upload(UploadBookDTO uploadDto, long id) {
        if (id == ADMIN_ID) {
            if (uploadDto.getAuthorId() != 0) {
                if (validateISBN(uploadDto.getIsbn())) {
                    if (!bookRepository.existsByIsbn(uploadDto.getIsbn())) {
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
                    throw new BadRequestException("Book has already been uploaded!");
                }
                throw new BadRequestException("Invalid ISBN!");
            }
            throw new BadRequestException("You have to first create the author!");
        }
        throw new UnauthorizedException("You can't upload books!");
    }


    //                  ----------EDIT BOOK-------

    public ShowBookDTO editBook(EditBookDTO dto, int uid) {
        Book book = getBookByISBN(dto.getIsbn());
        if (uid == ADMIN_ID) {
            if (bookRepository.existsByIsbn(dto.getIsbn())) {
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
        throw new UnauthorizedException("You can't delete books!");
    }


    //                        ----------Cover------------
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

    public boolean validateGenres(List<Integer> genres){
        Set<Integer> genreSet=genres.stream().collect(Collectors.toSet());
        if(genreSet.size()!=genres.size()){
            return false;
        }
        return true;
    }
    //                                 -------------OTHER------------------

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

  //                                --------QUERIES----------

    public ShowBookDTO getByIsbn(long isbn) {
        Book book = getBookByISBN(isbn);
        ShowBookDTO dto = modelMapper.map(book, ShowBookDTO.class);
        return dto;
    }

    public List<ShowBookDTO> getBooksByTitle(String str){
            List<Book> books = bookRepository.findByNameContainingIgnoreCase(str);
            List<ShowBookDTO> allBooksDTO = books.stream().
                    map(b -> modelMapper.map(b, ShowBookDTO.class)).
                    collect(Collectors.toList());
            return allBooksDTO;
    }

    public List<ShowBookDTO> getBooksByTitleAndAuthorKeyword(String keyword){
        List<ShowBookDTO> allBooks=getBooksByTitle(keyword);
        allBooks.addAll(getBooksByAuthorKeyword(keyword));
        Set<ShowBookDTO> set=new HashSet<>();
        set.addAll(allBooks);
        List<ShowBookDTO> result=new ArrayList<>();
        result.addAll(set);
        return result;
    }

    public List<ShowBookDTO> getBooksByAuthorKeyword(String keyword){
        List<Author> authors=getAuthorByKeyword(keyword);
        List<Book> books=new ArrayList<>();
        for (Author a:authors) {
            books.addAll(a.getAllBooks());
        }
        return books.stream().
        map(b -> modelMapper.map(b, ShowBookDTO.class)).
               collect(Collectors.toList());
    }

    public List<ShowBookDTO> getRecommendations(int userId) {
        User user = getUserById(userId);
        List<Genre> favGenres = user.getLikedGenres();
        Set<Book> userBooks = new HashSet<>();
        List<Author> favAuthors = new ArrayList<>();
        List<Book> booksForRecommendation = new ArrayList<>();
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
        return booksToRec.stream().
                map(b -> modelMapper.map(b, ShowBookDTO.class)).
                collect(Collectors.toList());
    }
}
