package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.services.CommentService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController extends  AbstractController{
    private CommentService commentService;

}
