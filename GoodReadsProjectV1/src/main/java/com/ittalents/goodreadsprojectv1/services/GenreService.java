package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreUsersDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.model.entity.Genre;
import com.ittalents.goodreadsprojectv1.model.entity.User;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.repository.GenreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService extends AbstractService {

    public List<GenreWithoutBooksDTO> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        List<GenreWithoutBooksDTO> allGenresDTO = genres.stream().
                map(g -> modelMapper.map(g, GenreWithoutBooksDTO.class)).
                collect(Collectors.toList());
        return allGenresDTO;
    }

    public GenreWithoutBooksDTO getById(int id) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new NotFoundException("Genre not found!"));
        GenreWithoutBooksDTO dto = modelMapper.map(genre, GenreWithoutBooksDTO.class);
        return dto;
    }

    public GenreWithoutBooksDTO getByName(String name) {
        Genre genre = genreRepository.findByName(name).orElseThrow(() -> new NotFoundException("Genre does not exist"));
        return modelMapper.map(genre, GenreWithoutBooksDTO.class);
    }

    public GenreUsersDTO like(int gid, int id) {
        Genre genre = getGenreById(gid);
        User user = getUserById(id);
        if (user.getLikedGenres().contains(genre)) {
            user.getLikedGenres().remove(genre);
        } else {
            user.getLikedGenres().add(genre);
        }
        userRepository.save(user);
        GenreUsersDTO response = modelMapper.map(genre, GenreUsersDTO.class);
        response.setUserLikedGenre(genre.getUserLikedGenre().stream().map(u -> modelMapper.map(u, UserWithoutRelationsDTO.class)).collect(Collectors.toList()));
        return response;
    }

}

