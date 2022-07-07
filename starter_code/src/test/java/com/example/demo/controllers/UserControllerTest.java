package com.example.demo.controllers;

import com.example.demo.SareetaApplication;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SareetaApplication.class)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private BCryptPasswordEncoder encoder;

    String USERNAME = "test";
    String PASSWORD = "testPass";
    String CONFIRMPASSWORD = "testPass";
    //@Ignore
    @Test
    public void contraseniaDebil(){
        String PASSWORDWEAK = "123456";
        CreateUserRequest request= new CreateUserRequest();
        request.setUsername(USERNAME);
        request.setPassword(PASSWORDWEAK);
        request.setConfirmpassword(PASSWORDWEAK);

        ResponseEntity<User> response = userController.createUser(request);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    //@Ignore
    @Test
    public  void contraseniaNoConfirmada(){
        String OTHERPASSWORD = "987654";
        CreateUserRequest request= new CreateUserRequest();
        request.setUsername(USERNAME);
        request.setPassword(PASSWORD);
        request.setConfirmpassword(OTHERPASSWORD);

        ResponseEntity<User> response = userController.createUser(request);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    //Voy a probar los metodos que estan en el UserController
    //findById
    @Test
    public void getUserById(){
        CreateUserRequest request= new CreateUserRequest();
        request.setUsername(USERNAME);
        request.setPassword(PASSWORD);
        request.setConfirmpassword(CONFIRMPASSWORD);

        ResponseEntity<User> response = userController.createUser(request);
        User user = response.getBody();

        assertNotNull(response);

        response = userController.findById(user.getId());
        assertNotNull(response);
        User userBuscado = response.getBody();
        assertEquals(user.getId(), userBuscado.getId());
    }

    //findByName
    @Test
    public void findUserByName(){
        CreateUserRequest request= new CreateUserRequest();
        request.setUsername(USERNAME);
        request.setPassword(PASSWORD);
        request.setConfirmpassword(CONFIRMPASSWORD);
        //Create useer to compared
        ResponseEntity<User> response = userController.createUser(request);
        User user = response.getBody();
        assertNotNull(response);

        //Find user by name
        ResponseEntity<User> retrieved = userController.findByUserName(user.getUsername());
        User userRetrieved = retrieved.getBody();

        assertNotNull(retrieved);
        assertEquals(user.getUsername(),userRetrieved.getUsername());

    }
}
