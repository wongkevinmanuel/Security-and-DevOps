package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.hibernate.boot.model.source.internal.hbm.AbstractSingularAttributeSourceEmbeddedImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UserControllerT {
    private UserController userController;

    private UserRepository usuarioRepositorio = mock(UserRepository.class);

    private CartRepository autoRepositorio = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    //@Before
    @Before
    public void configuracion(){
        userController = new UserController();
        //Obtiene todos los campos inyectados en mi objeto controlador usuario.
        TestUtils.InyectarObjeto(userController, "userRepository", usuarioRepositorio);
        TestUtils.InyectarObjeto(userController, "cartRepository", autoRepositorio);
        TestUtils.InyectarObjeto(userController, "bCryptPasswordEncoder", encoder);
    }

    //Prueba de cordura
    @Test
    public void crear_usuario_ruta_feliz() throws  Exception{
        //Se llama o se remplazara una determinada parte del codigo q se esta ejecutando aqui,
        //con el valor que se quiere agregar.
        //En otras palabras se preajusta para q cuando se ejecute mi prueba
        //em lugar del valor de ese fragmento de codigo en particular,
        //pondra todo lo que se asigne
        //Cada vez que se llama encoder.encode testtest,
        //puede reemplazar ese valor llamando a thenReturn
        //en esto y poner cualquier valor de mi eleccion.
        //stubbing.
        when(encoder.encode("testPass")).thenReturn("thiIsHashed");
        CreateUserRequest request= new CreateUserRequest();
        request.setUsername("test");
        request.setPassword("testPass");
        request.setConfirmpassword("testPass");

        //Aqui se llama al metodo que se esta probando
        ResponseEntity<User> response = userController.createUser(request);

        //Afirmaciones sobre el objeto que hace las solicitudes
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());//response.getStatusCodeValue());

        //Afirmaciones sobre el usuario creado
        User usuarioNuevo = response.getBody();

        assertNotNull(usuarioNuevo);
        assertEquals(0 ,usuarioNuevo.getId());
        assertEquals(request.getUsername(),usuarioNuevo.getUsername());
        assertEquals("thiIsHashed",usuarioNuevo.getPassword());
    }

    String USERNAME = "test";
    String PASSWORD = "testPass";
    String CONFIRMPASSWORD = "testPass";
    @Ignore
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

    @Ignore
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

        ResponseEntity<User> response;
        response = userController.createUser(request);
        User user = response.getBody();
        assertNotNull(response);

        response = userController.findById(user.getId());
        assertNotNull(response);
        User userBuscado = response.getBody();
        assertEquals(user.getId(), userBuscado.getId());
    }
}
