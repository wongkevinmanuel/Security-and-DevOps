package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerT {
    private UserController usuarioControlador;

    private UserRepository usuarioRepositorio = mock(UserRepository.class);

    private CartRepository autoRepositorio = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void configuracion(){
        usuarioControlador = new UserController();
        //Obtiene todos los campos inyectados en mi objeto controlador usuario.
        TestUtils.InyectarObjeto(usuarioControlador, "userRepository", usuarioRepositorio);
        TestUtils.InyectarObjeto(usuarioControlador, "cartRepository", autoRepositorio);
        TestUtils.InyectarObjeto(usuarioControlador, "bCryptPasswordEncoder", encoder);


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
        when(encoder.encode("testtest")).thenReturn("thiIsHashed");
        CreateUserRequest request= new CreateUserRequest();
        request.setUsername("test");
        request.setConfirmpassword("12345678");
        request.setConfirmpassword("1234567");

        //Aqui se llama al metodo que se esta probando
        final ResponseEntity<User> response = usuarioControlador.createUser(request);

        //Afirmaciones sobre el objeto que hace las solicitudes
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCodeValue());

        //Afirmaciones sobre el usuario creado
        User usuarioNuevo = response.getBody();

        assertNotNull(usuarioNuevo);
        assertEquals(0 ,usuarioNuevo.getId());
        assertEquals(request.getUsername(),usuarioNuevo.getUsername());
        assertEquals(request.getConfirmpassword(),usuarioNuevo.getPassword());
    }

}
