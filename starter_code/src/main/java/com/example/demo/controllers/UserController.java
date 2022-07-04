package com.example.demo.controllers;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/api/user")
public class UserController {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;

	//A
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		return ResponseEntity.of(userRepository.findById(id));
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}

	private String existenPropiedadesNull(@NotNull String usuario, String pass, String confirPass){

		if(usuario == null || usuario.isEmpty())
			return "User name";
		if(pass == null || pass.isEmpty())
			return "Password";
		if(confirPass == null || confirPass.isEmpty())
			return "Confirm Password";

		return new String();
	}

	private boolean contraseniaErrorODebil(CreateUserRequest createUserRequest){
		return (createUserRequest.getPassword().isEmpty()  || createUserRequest.getPassword().length() < 7)
				? true:false;
	}

	private boolean contraseniaConfirmada(String contrasenia, String confirmacionContrasenia){
		return contrasenia.equals(confirmacionContrasenia);
	}

	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		//log.info("Creating user {}", createUserRequest.getUsername());
		if(Objects.isNull(createUserRequest)) {
			log.error("No null user. Cannot create user.",
					new Exception(createUserRequest.getUsername()));
			return ResponseEntity.badRequest().build();
		}

		String nombreCampoNull = existenPropiedadesNull(createUserRequest.getUsername()
														,createUserRequest.getPassword()
														, createUserRequest.getConfirmpassword());
		if (!nombreCampoNull.isEmpty()){
			log.error("No null in "+ nombreCampoNull + ". Cannot create user {}.",
					new Exception(createUserRequest.getUsername()));
			return ResponseEntity.badRequest().build();
		}

		User user = new User();
		user.setUsername(createUserRequest.getUsername());


		if(contraseniaErrorODebil(createUserRequest)){
			log.error("Error with user password. Cannot create user."
						,new Exception(createUserRequest.getUsername()));
			return ResponseEntity.badRequest().build();
		}

		if (!contraseniaConfirmada(createUserRequest.getPassword()
				, createUserRequest.getConfirmpassword())){
			log.error("No confirm password. Cannot create user {}.",
						new Exception(createUserRequest.getUsername()));
			return ResponseEntity.badRequest().build();
		}

		user.setPassword(bCryptPasswordEncoder
							.encode(createUserRequest.getPassword()));

		Cart cart = new Cart();
		cartRepository.save(cart);
		user.setCart(cart);
		userRepository.save(user);
		log.info("User Saved! Id: "+user.getId());

		return ResponseEntity.ok(user);
		/*
		* Token de autenticacion
		* Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrZXZpbiIsImV4cCI6MTY1NjczMzU1Mn0.rJ_2WduL5sHrV0FsyESclabi8s9KvqXRs6HyEWadTsbdBQwJCuu7vG6dK0o_HXZ66NgWzP9hgxLFzg36KGfyjA
		* */
	}
	
}
