package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.model.dto.comments.CommentChangeDTO;
import com.ittalents.goodreadsprojectv1.model.dto.comments.CommentDTO;
import com.ittalents.goodreadsprojectv1.model.dto.comments.CommentReqCreateDTO;
import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewChangeDTO;
import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewDTO;
import com.ittalents.goodreadsprojectv1.model.dto.reviews.ReviewReqCreateDTO;
import com.ittalents.goodreadsprojectv1.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CommentController extends  AbstractController{
    @Autowired
    private CommentService commentService;

    @PostMapping("/comments")
    public CommentDTO createComment(@RequestBody CommentReqCreateDTO dto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return commentService.createNewComment(dto, id);
    }

    @DeleteMapping("/comments")
    public void deleteComment(@RequestParam int cid, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        commentService.deleteComment(cid, id);
    }

    @GetMapping("/comments")
    public CommentDTO getById(@RequestParam int cid) {
        return commentService.geById(cid);
    }

    @PutMapping("/comments")
    public CommentDTO editComment(@RequestBody CommentChangeDTO dto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return commentService.editComment(dto, id);
    }


}
