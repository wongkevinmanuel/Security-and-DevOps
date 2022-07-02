package com.example.demo.security;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

//Implementa la interfaz UserDetailsService y define solo un método
// que recupera el objeto Usuario de la base de datos:
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User usuario = repository.findByUsername(userName);

        if (Objects.isNull(usuario))
            throw new UsernameNotFoundException(userName);

        return new org.springframework.security.core.userdetails.User
                    (usuario.getUsername()
                        , usuario.getPassword()
                        , Collections.emptyList());
    }
}
