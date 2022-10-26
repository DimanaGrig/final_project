package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookDTO;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfChangeDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfReqCreateDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.model.entity.Shelf;
import com.ittalents.goodreadsprojectv1.model.entity.User;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ShelfService extends AbstractService {

    public void createBasicShelves(User u) {
        Shelf s1 = new Shelf();
        s1.setName("read");
        s1.setUser(u);
        s1.setFromBeggining(true);
        u.getUserShelves().add(s1);
        Shelf s2 = new Shelf();
        s2.setName("to_read");
        s2.setUser(u);
        s2.setFromBeggining(true);
        u.getUserShelves().add(s2);
        Shelf s3 = new Shelf();
        s3.setName("current_read");
        s3.setUser(u);
        s3.setFromBeggining(true);
        u.getUserShelves().add(s3);
        shelfRepository.save(s1);
        shelfRepository.save(s2);
        shelfRepository.save(s3);
    }

    public ShelfDTO createNewShelf(ShelfReqCreateDTO shelf, int id) {
        Shelf shelf1 = modelMapper.map(shelf, Shelf.class);
        User u = getUserById(id);
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
            System.out.println("sheld with id " + id + "have been deleted.");
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

//ne chete novo ime ot dto!
    public ShelfDTO editShelf(ShelfChangeDTO dto, int uid) {
        Shelf shelf = getShelfById(dto.getId());
        String name = dto.getNewName();
        BookDTO bookDTO= dto.getBook();
        if (shelf.getUser().getId() == uid) {
            if (bookDTO!= null) {
                shelf.getBooksAtThisShelf().add(modelMapper.map(dto.getBook(), Book.class));
            }
            if (!shelf.isFromBeggining()) {
                if (name!= null) {
                    shelf.setName(name);
                }
            } else {
                throw new UnauthorizedException("You don't have authorized!");
            }
        }
        shelfRepository.save(shelf);
        return modelMapper.map(shelf, ShelfDTO.class);
    }
}

