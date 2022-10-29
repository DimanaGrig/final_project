package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewChangeDTO;
import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewDTO;
import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewReqCreateDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfChangeDTO;
import com.ittalents.goodreadsprojectv1.model.dto.shelves.ShelfDTO;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import com.ittalents.goodreadsprojectv1.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ReviewController extends AbstractController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviews")
    public ReviewDTO createReview(@RequestBody ReviewReqCreateDTO dto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return reviewService.createNewReview(dto, id);
    }

    @DeleteMapping("/reviews/{rid}/delete")
    public void deleteReview(@PathVariable int rid, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        reviewService.deleteReview(rid, id);
    }

    @GetMapping("/reviews/{rid}")
    public ReviewDTO getById(@PathVariable int rid) {
        return reviewService.geById(rid);
    }

    @PutMapping("/reviews/edit")
    public ReviewDTO editReview(@RequestBody ReviewChangeDTO dto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return reviewService.editReview(dto, id);
    }


    @PostMapping("/reviews/{rid}/likes")
     public int like(@PathVariable int rid,HttpServletRequest request){
        int id = getLoggedUserId(request);
        checkLog(id);
        return reviewService.like(rid,id);
    }


}
