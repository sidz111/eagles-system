package com.eagle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.eagle.entities.User;
import com.eagle.repository.UserRepository;


@Controller
public class HomeController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("users", userRepository.findAll());
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User u = userRepository.findByEmail(username);
        model.addAttribute("user", u);
		return "index";
	}
	
	@GetMapping("/all-users")
	public String getAllUsers(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "all-users";
	}
	
	@GetMapping("/signin")
	public String signinPage() {
		return "signin";
	}
}
