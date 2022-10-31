package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewChangeDTO;
import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewDTO;
import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewReqCreateDTO;
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

    @DeleteMapping("/reviews")
    public void deleteReview(@RequestParam int rid, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        reviewService.deleteReview(rid, id);
    }

    @GetMapping("/reviews")
    public ReviewDTO getById(@RequestParam int rid) {
        return reviewService.geById(rid);
    }

    @PutMapping("/reviews")
    public ReviewDTO editReview(@RequestBody ReviewChangeDTO dto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return reviewService.editReview(dto, id);
    }


    @PutMapping("/reviews/sth")
     public int like(@RequestParam int rid,HttpServletRequest request){
        int id = getLoggedUserId(request);
        checkLog(id);
        return reviewService.like(rid,id);
    }


}
