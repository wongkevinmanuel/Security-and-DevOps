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

        requestCart.setItemId(2L);
        requestCart.setQuantity(1);
        responseCart = cartController.addTocart(requestCart);
        assertNotNull(responseCart);
        //assertEquals(HttpStatus.OK, responseCart.getStatusCode());
        responseCart = cartController.removeFromcart(requestCart);
        assertNotNull(responseCart);
        assertEquals(HttpStatus.OK, responseCart.getStatusCode());
    }

    @Test
    public void removeFromCart(){
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

        responseCart = cartController.removeFromcart(requestCart);
        assertNotNull(responseCart);
        assertEquals( 0,responseCart.getBody().getItems().size());
    }

    @Test
    public void errorAddToCartUserNull(){
        ModifyCartRequest requestCart = new ModifyCartRequest();
        requestCart.setUsername(null);
        requestCart.setItemId(1);
        requestCart.setQuantity(4);

        ResponseEntity<Cart> responseCart = cartController.addTocart(requestCart);
        assertNotNull(responseCart);
        assertEquals(HttpStatus.NOT_FOUND, responseCart.getStatusCode());
    }
}
