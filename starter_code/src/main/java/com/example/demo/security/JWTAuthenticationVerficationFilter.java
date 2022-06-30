package com.example.demo.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.auth0.jwt.JWT;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;


public class JWTAuthenticationVerficationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationVerficationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

    //Valida el token leído del encabezado de autorización.
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
        String token = req.getHeader(JWTPersonalSecurityConstants.HEADER_STRING);
        if (token != null) {
            String user = JWT.require(HMAC512(JWTPersonalSecurityConstants.SECRET.getBytes())).build()
                    .verify(token.replace(JWTPersonalSecurityConstants.TOKEN_PREFIX, ""))
                    .getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }

    //Este método se usa cuando tenemos varios roles y una política para RBAC.
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String header = req.getHeader(JWTPersonalSecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(JWTPersonalSecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

}
