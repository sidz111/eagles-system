package com.eagle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eagle.entities.User;
import com.eagle.repository.UserRepository;
import com.eagle.service.ProjectService;
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

	@Autowired
	ProjectService projectService;

	@GetMapping("/all-projects")
	public String allProjects(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User employee = userRepository.findByEmail(username);
		model.addAttribute("projects", employee.getProjects());
		return "employees/projects";
	}
	
	@GetMapping("/all-reportings")
    public String getAllReportings(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username);
        model.addAttribute("reportings", userLogsService.getUserLogsByUserId(user.getId()));
    	return "employees/all-reportings";
    }
}
