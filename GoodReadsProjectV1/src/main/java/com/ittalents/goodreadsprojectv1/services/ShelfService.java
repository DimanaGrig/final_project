package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.entity.Shelf;
import com.ittalents.goodreadsprojectv1.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShelfService extends AbstractService {

    public void createBasicShelves(User u) {
        Shelf s1 = new Shelf();
        s1.setName("read");
        s1.setUser(u);
        s1.setFromBeggining(true);
        Shelf s2 = new Shelf();
        s2.setName("to_read");
        s2.setUser(u);
        s2.setFromBeggining(true);
        Shelf s3 = new Shelf();
        s3.setName("current_read");
        s3.setUser(u);
        s3.setFromBeggining(true);
        shelfRepository.save(s1);
        shelfRepository.save(s2);
        shelfRepository.save(s3);
    }


}
