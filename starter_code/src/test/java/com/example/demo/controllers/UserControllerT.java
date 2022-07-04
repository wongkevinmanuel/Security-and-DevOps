package com.example.demo.controllers;

import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class UserControllerT {
    private UserController usuarioControlador;

    private UserRepository usuarioRepositorio = mock(UserRepository.class);

    private CartRepository autoRepositorio = mock(CartRepository.class);

    @Before
    public void establecerUp(){

    }

    @Test
    public void crear_usuario_ruta_feliz(){

    }

}
