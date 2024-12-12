package com.eagle.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eagle.entities.User;
import com.eagle.entities.UserLogs;
import com.eagle.repository.UserRepository;
import com.eagle.service.ProjectService;
import com.eagle.service.UserLogsService;


@Controller
public class HomeController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserLogsService userLogsService;
	
	@Autowired
	ProjectService projectService;
	
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
	
	
	@GetMapping("/add-log")
    public String addLog(Model model) {
    	return "add-log";
    }
    
    @PostMapping("/add-log")
    public String addLogPost(
    		@RequestParam("logDescription") String logDescription,
    		RedirectAttributes redirectAttributes) {
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User employee = userRepository.findByEmail(username);
        
        UserLogs logs = new UserLogs();
        logs.setLogDescription(logDescription);
        logs.setTimestamp(new Date().toString());
        logs.setUser(employee);
        userLogsService.addUserLogs(logs);
    	return "redirect:/";
    }
    
    @GetMapping("/all-reportings")
    public String getAllReportings(Model model) {
    	model.addAttribute("reportings", userLogsService.getAllUserLogs());
    	return "all-reportings";
    }
    
    @GetMapping("/all-projects")
	public String getallProjects(Model model) {
		model.addAttribute("projects", projectService.getAllProjects());
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username);
        model.addAttribute("user", user);
		return "all-projects";
	}
    
    @GetMapping("/profile")
    public String getProfile(Model model) {
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username);
        model.addAttribute("user", user);
    	return "profile";
    }
}
