package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.example.demo.model.persistence.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sun.security.util.SecurityConstants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

//Esta clase personalizada es responsable del proceso de autenticación.
// Esta clase amplía la clase UsernamePasswordAuthenticationFilter,
// que está disponible en las dependencias spring-security-web y
// spring-boot-starter-web. La clase Base analiza las credenciales
// del usuario (nombre de usuario y contraseña).
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    Log log = LogFactory.getLog(this.getClass());
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager aM){
        this.authenticationManager = aM;
    }

    //realiza la autenticación real mediante el análisis (también
    // llamado filtrado) de las credenciales del usuario.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            User credentials = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword(),
                            new ArrayList<>()));
        } catch (IOException e) {
            logger.error("Error - IOException on auth", e);
            throw new RuntimeException(e);
        }
    }

    //este método está originalmente presente en el padre de la clase
    // Base. Después de anular, se llamará a este método después
    // de que un usuario inicie sesión correctamente. A continuación,
    // está generando un token de cadena (JWT) para este usuario.
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String token = JWT.create()
                .withSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWTPersonalSecurityConstants.EXPIRATION_TIME))
                .sign(HMAC512(JWTPersonalSecurityConstants.SECRET.getBytes()));
        res.addHeader(JWTPersonalSecurityConstants.HEADER_STRING, JWTPersonalSecurityConstants.TOKEN_PREFIX + token);
    }


}
