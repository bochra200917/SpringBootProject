package com.example.gestimmob;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.gestimmob.business.services.UserService;
import com.example.gestimmob.dao.entities.User;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GestimmobApplication {

	@Autowired
	UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(GestimmobApplication.class, args);
	}

// 	@PostConstruct
//     public void init() {
//         List<String> roles = new ArrayList<String>();
//         roles.add("USER");
// 		roles.add("ADMIN");
//         userService.saveUser(new User(null, "sarra", "sarra", "sarra@gmail.com", roles));
//     } 
}
