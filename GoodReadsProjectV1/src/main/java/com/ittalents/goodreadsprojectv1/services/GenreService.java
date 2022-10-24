package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.model.entity.Genre;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.repository.GenreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<GenreWithoutBooksDTO> getAllGenres(){
        List<Genre> genres=genreRepository.findAll();
        List<GenreWithoutBooksDTO> allGenresDTO = genres.stream().
                map(g -> modelMapper.map(g, GenreWithoutBooksDTO.class)).
                collect(Collectors.toList());
        return allGenresDTO;
    }

    public GenreWithoutBooksDTO getById(int id){
        Genre genre=genreRepository.findById(id).orElseThrow(()-> new NotFoundException("Genre not found!"));
        GenreWithoutBooksDTO dto=modelMapper.map(genre, GenreWithoutBooksDTO.class); // todo should I save here
        return dto;
    }
    public GenreWithoutBooksDTO getByName(String name){
        Genre genre=genreRepository.findByName(name).orElseThrow(()->new NotFoundException("Genre does not exist"));
        return modelMapper.map(genre, GenreWithoutBooksDTO.class);
    }
}
