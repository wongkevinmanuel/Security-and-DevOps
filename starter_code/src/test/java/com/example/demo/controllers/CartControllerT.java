package com.example.demo.controllers;

import com.example.demo.SareetaApplication;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SareetaApplication.class)
public class CartControllerT {

    @Autowired
    private UserController userController;

    @Autowired
    private CartController cartController;

    //addTocart
    @Test
    public void addToCart(){
        CreateUserRequest request= new CreateUserRequest();
        request.setUsername("USERNAME");
        request.setPassword("PASSWORD");
        request.setConfirmpassword("PASSWORD");

        ResponseEntity<User> response = userController.createUser(request);
        User user = response.getBody();

        assertNotNull(response);

        //Agregar objeto Cart
        ModifyCartRequest requestCart = new ModifyCartRequest();
        requestCart.setUsername(user.getUsername());
        requestCart.setItemId(1);
        requestCart.setQuantity(2);

        ResponseEntity<Cart> responseCart = cartController.addTocart(requestCart);

        assertNotNull(responseCart);
        assertEquals(HttpStatus.OK, responseCart.getStatusCode());
        assertNotNull(responseCart.getBody());

    }

    //removeFromCart
}
