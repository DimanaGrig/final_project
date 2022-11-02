package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.controller.ShelfController;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfAddBookDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfChangeDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfReqCreateDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.model.entity.Shelf;
import com.ittalents.goodreadsprojectv1.model.entity.User;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShelfService extends AbstractService {

    public static final String[] basicShelves = {"read", "to read", "current_read"};

    public void createBasicShelves(User u) {
        for (int i = 0; i < basicShelves.length; i++) {
            Shelf s1 = new Shelf();
            s1.setName(basicShelves[i]);
            s1.setUser(u);
            s1.setFromBeggining(true);
            u.getUserShelves().add(s1);
            shelfRepository.save(s1);
        }
    }

    public ShelfDTO createNewShelf(ShelfReqCreateDTO shelf, int id) {
        User u = getUserById(id);
        List<Shelf> shelfList = u.getUserShelves();
        for (int i = 0; i < shelfList.size(); i++) {
            if (shelfList.get(i).getName().equals(shelf.getName())) {
                throw new BadRequestException("You already have shelf with that name.");
            }
        }
        Shelf shelf1 = modelMapper.map(shelf, Shelf.class);
        shelf1.setUser(u);
        shelfRepository.save(shelf1);
        u.getUserShelves().add(shelf1);
        ShelfDTO response = modelMapper.map(shelf1, ShelfDTO.class);
        UserWithoutRelationsDTO user = modelMapper.map(shelf1.getUser(), UserWithoutRelationsDTO.class);
        response.setUser(user);
        return response;
    }

    public void deleteShelf(int sid, int id) {
        Shelf shelf = getShelfById(sid);
        if (shelf.getUser().getId() == id && !shelf.isFromBeggining()) {
            shelfRepository.deleteById(sid);
            System.out.println("sheld with id " + id + "has been deleted.");
        } else {
            throw new UnauthorizedException("You can't delete this shelf!");
        }
    }

    public ShelfDTO geById(int sid) {
        Shelf shelf = getShelfById(sid);
        ShelfDTO dto = modelMapper.map(shelf, ShelfDTO.class);
        UserWithoutRelationsDTO user = modelMapper.map(shelf.getUser(), UserWithoutRelationsDTO.class);
        dto.setUser(user);
        dto.setBooksAtThisShelf(shelf.getBooksAtThisShelf().stream().map(book -> modelMapper.map(book, BookWithoutRelationsDTO.class)).collect(Collectors.toList()));
        return dto;
    }

    public ShelfDTO editShelf(ShelfChangeDTO dto, int uid) {
        Shelf shelf = getShelfById(dto.getId());
        if (shelf.getUser().getId() == uid) {
            if (!shelf.isFromBeggining() && shelf.getName().equals(dto.getName())) {
                if (dto.getNewName() != null) {
                    shelf.setName(dto.getNewName());
                    shelfRepository.save(shelf);
                } else {
                    throw new BadRequestException("You have to choose a new name for this shelf!");
                }
            } else {
                throw new UnauthorizedException("You can't do this!");
            }
        } else {
            throw new UnauthorizedException("This shelf is not your to change it!");
        }
        return modelMapper.map(shelf, ShelfDTO.class);
    }

    public ShelfDTO addBookToShelf(ShelfAddBookDTO dto, int uid) {
        User user = getUserById(uid);
        Shelf shelf = getShelfById(dto.getId());
        if (!(shelf.getUser().getId() == user.getId())) {
            throw new UnauthorizedException("This shelf is not yours.");
        }
        Book book = getBookByISBN(dto.getIsbn());
        if (shelf.getBooksAtThisShelf().contains(book)) {
            throw new BadRequestException("You already have this book at that Shelf.");
        }
        shelf.getBooksAtThisShelf().add(book);
        shelfRepository.save(shelf);
        List<Book> books = shelf.getBooksAtThisShelf();
        ShelfDTO dto1 = modelMapper.map(shelf, ShelfDTO.class);
        dto1.setBooksAtThisShelf(books.stream().map(book1 -> modelMapper.map(book1, BookWithoutRelationsDTO.class)).collect(Collectors.toList()));
        return dto1;
    }
}



