package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfAddBookDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfChangeDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfReqCreateDTO;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.services.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ShelfController extends AbstractController {
    @Autowired
    private ShelfService shelfService;

    @PostMapping("/shelves")
    public ShelfDTO createShelf(@RequestBody ShelfReqCreateDTO shelf, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return shelfService.createNewShelf(shelf, id);
    }

    @DeleteMapping("/shelves")
    public void deleteShelf(@RequestParam int sid, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        shelfService.deleteShelf(sid, id);
    }

    @GetMapping("/shelves")
    public ShelfDTO getById(@RequestParam int sid) {
        return shelfService.geById(sid);
    }

    @PutMapping("/shelves")
    public ShelfDTO editShelf(@RequestBody ShelfChangeDTO dto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return shelfService.editShelf(dto, id);
    }

    @PutMapping("/shelves/ed")
    public ShelfDTO addBookToShelf(@RequestBody ShelfAddBookDTO dto, HttpServletRequest request) {
        if (dto.getId() ==  0) {
            throw new BadRequestException("Please, go and create a shelf or choose some already exist.");
        }
        int id = getLoggedUserId(request);
        checkLog(id);
        return shelfService.addBookToShelf(dto, id);
    }

}


