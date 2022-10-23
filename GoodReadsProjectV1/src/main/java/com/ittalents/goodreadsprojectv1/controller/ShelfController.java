package com.ittalents.goodreadsprojectv1.controller;

import com.ittalents.goodreadsprojectv1.services.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShelfController  extends AbstractController{

    @Autowired
    private ShelfService shelfService;


}
