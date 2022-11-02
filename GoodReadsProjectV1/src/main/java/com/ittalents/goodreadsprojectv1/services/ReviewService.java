package com.ittalents.goodreadsprojectv1.services;


import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.book_dtos.BookWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.comments.CommentWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewChangeDTO;
import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewDTO;
import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewReqCreateDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfReqCreateDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.UserWithoutRelationsDTO;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import com.ittalents.goodreadsprojectv1.model.entity.Review;
import com.ittalents.goodreadsprojectv1.model.entity.Shelf;
import com.ittalents.goodreadsprojectv1.model.entity.User;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.stream.Collectors;

@Service
public class ReviewService extends AbstractService {
    @Autowired
    private ShelfService shelfService;

    @Transactional
    public ReviewDTO createNewReview(ReviewReqCreateDTO dto, int id) {
        if (dto.getShelfId() == 0 && dto.getShelfName() == null) {
            throw new BadRequestException("You need to select a shelf or create a new one.");
        }
        Review review = new Review();
        User user = getUserById(id);
        Book book = getBookByISBN(dto.getBookIsbn());
        checkForLength(dto.getOpinion());
        checkValidRate(dto.getRate());
        if (dto.getShelfId() == 0) {
            String shelfName = dto.getShelfName();
            ShelfReqCreateDTO shelf = new ShelfReqCreateDTO();
            shelf.setName(shelfName);
            ShelfDTO dto1 = shelfService.createNewShelf(shelf, id);
            dto.setShelfId(dto1.getId());
        }
        Shelf theShelf = getShelfById(dto.getShelfId());
        if (!(user.getUserShelves().contains(theShelf))) {
            throw new BadRequestException("You can add book in that shelf.");
        }
        if (!(theShelf.getBooksAtThisShelf().contains(book))) {
            theShelf.getBooksAtThisShelf().add(book);
        }
        shelfRepository.save(theShelf);
        review.setUser(user);
        review.setBook(book);
        review.setOpinion(dto.getOpinion());
        review.setRate(dto.getRate());
        reviewRepository.save(review);
        return modelMapper.map(review, ReviewDTO.class);
    }

    public void deleteReview(int rid, int id) {
        Review review = getReviewById(rid);
        if (review.getUser().getId() == id) {
            reviewRepository.deleteById(rid);
            System.out.println("Review with id " + id + "has been deleted.");
        } else {
            throw new UnauthorizedException("You can't delete this review!");
        }
    }

    public ReviewDTO editReview(ReviewChangeDTO dto, int id) {
        Review review = getReviewById(dto.getId());
        if (review.getUser().getId() != id) {
            throw new UnauthorizedException("This review is not yours to change it!");
        }
        checkForLength(dto.getOpinion());
        checkValidRate(dto.getRate());
        review.setOpinion(dto.getOpinion());
        review.setRate(dto.getRate());
        reviewRepository.save(review);
        return modelMapper.map(review, ReviewDTO.class);
    }

    public ReviewDTO geById(int rid) {
        Review review = getReviewById(rid);
        ReviewDTO dto = modelMapper.map(review, ReviewDTO.class);
        UserWithoutRelationsDTO user = modelMapper.map(review.getUser(), UserWithoutRelationsDTO.class);
        dto.setUser(user);
        dto.setBook(modelMapper.map(review.getBook(), BookWithoutRelationsDTO.class));
        dto.getBook().setAuthor(modelMapper.map(review.getBook().getAuthor(), AuthorWithoutRelationsDTO.class));
        dto.setCommentsForThisReview(review.getCommentsForThisReview().stream()
                .map(comment -> modelMapper.map(comment, CommentWithoutRelationsDTO.class))
                .collect(Collectors.toList()));
        return dto;
    }

    public int like(int rid, int id) {
        Review review = getReviewById(rid);
        User user = getUserById(id);
        if (user.getLikedReviews().contains(review)) {
            user.getLikedReviews().remove(review);
        } else {
            user.getLikedReviews().add(review);
        }
        userRepository.save(user);
        return review.getUsersLikedThisReview().size();

    }
}
