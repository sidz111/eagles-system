package com.eagle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.eagle.entities.User;
import com.eagle.entities.UserLogs;
import com.eagle.repository.UserRepository;
import com.eagle.service.UserLogsService;
import com.eagle.service.UserService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private UserLogsService userLogsService;
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/all-projects")
    public String allProjects(Model model) {
    	 String username = SecurityContextHolder.getContext().getAuthentication().getName();
         User employee = userRepository.findByEmail(username);
         model.addAttribute("projects", employee.getProjects());
    	return "employees/projects";
    }
}
