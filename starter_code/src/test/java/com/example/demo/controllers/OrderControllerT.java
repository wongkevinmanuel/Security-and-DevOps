package com.example.demo.controllers;

import com.example.demo.SareetaApplication;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SareetaApplication.class)
public class OrderControllerT {

    @Autowired
    private OrderController orderController;

    @Autowired
    private UserController userController;

    @Autowired
    private CartController cartController;

    final String USERNAME = "test";
    final String PASSWORD = "testPass";
    final String CONFIRMPASSWORD = "testPass";

    //submit
    @Test
    public void submitOrder(){
        CreateUserRequest request= new CreateUserRequest();
        request.setUsername(USERNAME);
        request.setPassword(PASSWORD);
        request.setConfirmpassword(CONFIRMPASSWORD);
        //CREATE USER
        ResponseEntity<User> userResponse = userController.createUser(request);
        User user = userResponse.getBody();
        Assert.assertNotNull(userResponse);
        Assert.assertEquals(HttpStatus.OK, userResponse.getStatusCode());

        //CREATE ITEM CART
        ModifyCartRequest requestCart = new ModifyCartRequest();
        requestCart.setUsername(user.getUsername());
        requestCart.setItemId(1);
        requestCart.setQuantity(2);

        ResponseEntity<Cart> responseCart = cartController.addTocart(requestCart);
        Assert.assertNotNull(responseCart);
        Assert.assertEquals(HttpStatus.OK,responseCart.getStatusCode());

        //SUBMIT ORDER
        ResponseEntity<UserOrder> userOrderResponse = orderController.submit(user.getUsername());
        Assert.assertNotNull(userOrderResponse);
        Assert.assertEquals(HttpStatus.OK, userOrderResponse.getStatusCode());
    }

    @Test
    public void getOrdersForUsers(){
        CreateUserRequest request= new CreateUserRequest();
        request.setUsername(USERNAME);
        request.setPassword(PASSWORD);
        request.setConfirmpassword(CONFIRMPASSWORD);
        //CREATE USER
        ResponseEntity<User> userResponse = userController.createUser(request);
        User user = userResponse.getBody();
        Assert.assertNotNull(userResponse);
        Assert.assertEquals(HttpStatus.OK, userResponse.getStatusCode());

        //CREATE ITEM CART
        ModifyCartRequest requestCart = new ModifyCartRequest();
        requestCart.setUsername(user.getUsername());
        requestCart.setItemId(1);
        requestCart.setQuantity(2);

        ResponseEntity<Cart> responseCart = cartController.addTocart(requestCart);
        Assert.assertNotNull(responseCart);
        Assert.assertEquals(HttpStatus.OK,responseCart.getStatusCode());

        //SUBMIT ORDER
        ResponseEntity<UserOrder> userOrderResponse = orderController.submit(user.getUsername());
        Assert.assertNotNull(userOrderResponse);
        Assert.assertEquals(HttpStatus.OK, userOrderResponse.getStatusCode());

        //GET ORDER FOR USER
        ResponseEntity< List<UserOrder> > responseUserOrder = orderController.getOrdersForUser(user.getUsername());
        Assert.assertEquals(HttpStatus.OK, responseUserOrder.getStatusCode());
        List<UserOrder> orders = responseUserOrder.getBody();
        Assert.assertNotNull(orders);
        Assert.assertEquals(1, orders.size());
    }

}
