package com.example.demo.controllers;

import com.example.demo.SareetaApplication;
import com.example.demo.model.persistence.Item;
import com.fasterxml.jackson.databind.jsontype.impl.AsExistingPropertyTypeSerializer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SareetaApplication.class)
public class ItemControllerT {

    @Autowired
    private ItemController itemController;

    @Test
    public void getAllItems(){
        ResponseEntity<List<Item> > item = itemController.getItems();
        Assert.assertNotNull(item);
        Assert.assertEquals(HttpStatus.OK, item.getStatusCode());
        Assert.assertEquals(2,item.getBody().size());
    }

}
