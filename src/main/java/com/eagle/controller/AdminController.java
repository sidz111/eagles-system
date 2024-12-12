package com.eagle.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eagle.entities.Project;
import com.eagle.entities.User;
import com.eagle.entities.UserLogs;
import com.eagle.service.ProjectService;
import com.eagle.service.UserLogsService;
import com.eagle.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserLogsService userLogsService;

	@GetMapping("/add-user")
	public String showAddUserForm(Model model) {
		model.addAttribute("user", new User());
		return "admins/add-user";
	}

	@PostMapping("/add-user")
	public String addUser(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("contact") String contact,
			@RequestParam("role") String role, @RequestParam("joinDate") String joinDate,
			@RequestParam("designation") String designation, @RequestParam("profileImage") MultipartFile profileImage,
			RedirectAttributes redirectAttributes) {
		User u = new User();
		u.setName(name);
		u.setEmail(email);
		u.setPassword(passwordEncoder.encode(password));
		u.setContact(contact);
		u.setRole(role);
		u.setJoinDate(joinDate);
		u.setDesignation(designation);
		u.setIsAccountLocked(false);
		u.setProfileImage(profileImage.getOriginalFilename());
		try {
			File saveFile = new File("src/main/resources/static/images/users");

			if (!saveFile.exists()) {
				saveFile.mkdirs();
			}

			Path path = Paths.get(saveFile.getAbsolutePath(), profileImage.getOriginalFilename());
			Files.copy(profileImage.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			System.out.println("File saved at: " + path.toString());

			// Save the relative path in the user entity
			u.setProfileImage(profileImage.getOriginalFilename());
			userService.addUser(u);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/";
	}

	@GetMapping("/delete-user/{id}")
	public String deleteUser(@PathVariable Long id) {
		List<UserLogs> logs = (List<UserLogs>) userLogsService.getUserLogsByUserId(id);
		List<Project> projects = projectService.getProjectsByUserId(id);
		logs.clear();
		projects.clear();
		userService.deleteUserById(id);
		return "redirect:/all-users";
	}
}
