package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreNameDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreUsersDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class GenreController extends AbstractController {
    @Autowired
    private GenreService genreService;

    @GetMapping("/genres")
    public List<GenreWithoutBooksDTO> getAllGenres(HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        List<GenreWithoutBooksDTO> allGenresDTO = genreService.getAllGenres();
        return allGenresDTO;
    }

    @GetMapping("/genres/{id}")
    public GenreWithoutBooksDTO getById(@PathVariable int id) {
        return genreService.getById(id);
    }

    @PutMapping("/genres")
    public GenreUsersDTO like(@RequestParam int gid, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return genreService.like(gid, id);
    }
    @PostMapping("/genres")
    public GenreWithoutBooksDTO createGenre(@RequestBody GenreNameDTO dto, HttpServletRequest request){
        int id = getLoggedUserId(request);
        checkLog(id);
        return genreService.createGenre(id, dto);
    }
    @DeleteMapping("/genres/{id}")
    public void deleteGenre(HttpServletRequest request, @PathVariable int id) {
        int uid = getLoggedUserId(request);
        checkLog(uid);
        genreService.deleteGenre(id, uid);
    }

}
