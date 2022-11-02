package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreNameDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreUsersDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Genre;
import com.ittalents.goodreadsprojectv1.model.entity.User;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
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
    public GenreWithoutBooksDTO createGenre(int uid, GenreNameDTO dto){
        if(uid!=ADMIN_ID){
            throw new UnauthorizedException("You can't add genres!");
        }
        String dtoName= dto.getName();
        if(genreRepository.existsByNameEqualsIgnoreCase(dtoName)){
            throw new BadRequestException("Genre with this name already exists!");
        }
        if(!validateName(dto.getName())){
            throw new BadRequestException("Invalid input for name!");
        }
        Genre newGenre=modelMapper.map(dto, Genre.class);
        genreRepository.save(newGenre);
        return modelMapper.map(newGenre, GenreWithoutBooksDTO.class);
    }

    public void deleteGenre(int id, int uid) {
        if (uid == ADMIN_ID) {
            genreRepository.deleteById(id);
            System.out.println("Genre with id = " + id + " has been deleted!");
            return;
        }
        throw new UnauthorizedException("You can't delete genres!");
    }
}

