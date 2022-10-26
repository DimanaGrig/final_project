package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewDTO;
import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewReqCreateDTO;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import com.ittalents.goodreadsprojectv1.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ReviewController extends AbstractController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviews")
    public ReviewDTO createReview(ReviewReqCreateDTO dto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return reviewService.createNewReview(dto, id);
    }

//    @PostMapping("/reviews/{rid}/likes")


}
