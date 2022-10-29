package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookDTO;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookWithoutQuotesDTO;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.ShowBookDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Author;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.model.entity.Genre;
import com.ittalents.goodreadsprojectv1.model.entity.Shelf;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.repository.AuthorRepository;
import com.ittalents.goodreadsprojectv1.model.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BookService extends  AbstractService{
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public ShowBookDTO upload(BookDTO uploadDto) {
        long isbn = uploadDto.getIsbn();
        String name = uploadDto.getName();
        AuthorWithoutBooksDTO authorDto = uploadDto.getAuthor();
        Author author = modelMapper.map(authorDto, Author.class);
        if (validateISBN(isbn)) {
            if (!bookRepository.existsByIsbn(uploadDto.getIsbn())) {
                if (!validateName(name) || !validateAuthor(authorDto)) {
                    throw new BadRequestException("Invalid title or author name!");
                } else {
                    Book book = modelMapper.map(uploadDto, Book.class);
                    if (!authorRepository.existsByFirstNameAndLastName(author.getFirstName(), author.getLastName())) {
                        authorRepository.save(author);
                    }
                    book.setName(name);
                    book.setAuthor(author);
                    bookRepository.save(book);
                    ShowBookDTO result=new ShowBookDTO();
                    result.setIsbn(uploadDto.getIsbn());
                    result.setAuthor(uploadDto.getAuthor());
                    result.setName(uploadDto.getName());
                    return result;
                }
            }
        }
        throw new BadRequestException("Book has already been uploaded!");
    }

    //                  ----------EDIT BOOK-------
    public ShowBookDTO editContent(BookDTO editDTO, String newContent){
        if(validSize(newContent)){
            editDTO.setContent(newContent); // todo - да го мапна и запазя? тогава в базата няма ли да са 2 книги?
            ShowBookDTO result=new ShowBookDTO();
            result.setContent(editDTO.getContent());
            return result;
        }
        throw new BadRequestException("Too many characters!");
    }
    public ShowBookDTO editAdditionalInfo(BookDTO editDTO, String newAdditionalInfo){
        if(validSize(newAdditionalInfo)){
            editDTO.setContent(newAdditionalInfo);
            ShowBookDTO result=new ShowBookDTO();
            result.setAdditionalInfo(editDTO.getAdditionalInfo());
            return result;
        }
        throw new BadRequestException("Too many characters!");
    }
    public BookWithoutQuotesDTO selectGenres(BookDTO editDTO, Set<Genre> selectedGenres){
        // todo
        BookWithoutQuotesDTO result=new BookWithoutQuotesDTO();
        ////////////////////////////////
        return result;
    }


//                              ----------VALIDATION METHODS-----------
    public boolean validateISBN(long isbn){
        String strNumber=String.valueOf(isbn);
        int sum = 0;
        int i, t, intNumber, dNumber;

        if (strNumber.length() != 10) {
            return false;
        }
        for (i = 0; i < strNumber.length(); i++) {
            intNumber = Integer.parseInt(strNumber.substring(i, i+1));
            dNumber = i + 1;
            t = dNumber * intNumber;
            sum = sum + t;
        }
        if ((sum % 11) == 0) {
            return true;
        }
        return false;
    }

    public boolean validateName(String name){
        if(validateLength(name.length())){
            return false;
        }
        return true;
    }
    public boolean validateAuthor(AuthorWithoutBooksDTO author){
        int fullNameLength= author.getFirstName().length()+author.getLastName().length();
        if(validateNoSpecialChars(author.getFirstName())&& validateNoSpecialChars(author.getLastName())
                && validateLength(fullNameLength)){
            return true;
        }
        return false;
    }

    public boolean validateNoSpecialChars(String string){
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        boolean b = m.find(); //it has special chars
        if(b){
            return false;
        }
        return true;
    }
    public boolean validateLength(int length){
        if(length<=100){
            return true;
        }
        return false;
    }

    public boolean validSize(String str){
        if(str.length()>600){
            return false;
        }
        return true;
    }
    public List<BookDTO> getAllBooks(){
        List<Book> books=bookRepository.findAll();
        List<BookDTO> allBooksDTO = books.stream().
                map(b -> modelMapper.map(b, BookDTO.class)).
                collect(Collectors.toList());
        return allBooksDTO;
    }

    public BookDTO getByIsbn(long isbn){
        Book book=bookRepository.findByIsbn(isbn).orElseThrow(()-> new NotFoundException("Book not found!"));
        BookDTO dto=modelMapper.map(book, BookDTO.class);
        dto.setName(book.getName());      // todo is this one necessary
        return dto;
    }
    public List<BookDTO> getByName(String name){
        List<Book> allBooks=bookRepository.findByName("%"+name+"%");
        if(allBooks.isEmpty()){
            throw new NotFoundException("Can't find book with this title!");
        }
        return allBooks.stream()
                .map(b -> modelMapper.map(allBooks, BookDTO.class))
                .collect(Collectors.toList());
    }
//
//    public BookDTO addBookToShelf(int sid,int uid,long isbn){
//        Book book = getBookByISBN(isbn);
//        Shelf shelf = getShelfById(sid);
//        shelf.getBooksAtThisShelf().add(book);
//        shelf.
//
//    }
}
