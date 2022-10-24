package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.services.BookService;
import com.ittalents.goodreadsprojectv1.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenreController extends  AbstractController{
    @Autowired
    private GenreService genreService;
    @GetMapping("/genres")
    public List<GenreWithoutBooksDTO> getAllGenres() {
        List<GenreWithoutBooksDTO> allGenresDTO = genreService.getAllGenres();
        return allGenresDTO;
    }
    @GetMapping("/genres/{id}")
    public GenreWithoutBooksDTO getById(@PathVariable int id){
        return genreService.getById(id);
    }


}
