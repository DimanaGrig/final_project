package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfChangeDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfReqCreateDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserRespWithoutPassDTO;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
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

    @DeleteMapping("/shelves/{sid}/delete")
    public void deleteShelf(@PathVariable int sid, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        shelfService.deleteShelf(sid, id);
    }

    @GetMapping("/shelves/{sid}")
    public ShelfDTO getById(@PathVariable int sid) {
        return shelfService.geById(sid);
    }

    @PutMapping("/shelves/edit")
    public ShelfDTO editShelf(@RequestBody ShelfChangeDTO dto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return shelfService.editShelf(dto, id);
    }

}


