package com.eagle.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.eagle.repository.UserRepository;
import com.eagle.service.ProjectService;
import com.eagle.service.UserLogsService;
import com.eagle.service.UserService;

import jakarta.mail.internet.MimeMessage;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	ProjectService projectService;

	@Autowired
	UserLogsService userLogsService;

	@Autowired
	JavaMailSender javaMailSender;

	@GetMapping("/add-user")
	public String showAddUserForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("managers", userService.getUserByRole("ROLE_MANAGER"));
		return "admins/add-user";
	}

	@PostMapping("/add-user")
	public String addUser(Model model, @RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("contact") String contact,
			@RequestParam("role") String role, @RequestParam("joinDate") String joinDate,
			 @RequestParam("under") Long managerId, @RequestParam("designation") String designation,
			@RequestParam("profileImage") MultipartFile profileImage, RedirectAttributes redirectAttributes) {
		if (userService.getUserByEmail(email) != null) {
			model.addAttribute("error", email + "already used!!!");
			return "redirect:/admin/add-user";
		} else {
			User u = new User();
			u.setRandomId(new Random().nextLong(600000));
			u.setName(name);
			u.setEmail(email);
			u.setPassword(passwordEncoder.encode(password));
			u.setContact(contact);
			u.setRole(role);
			u.setJoinDate(joinDate);
			u.setDesignation(designation);
			u.setIsAccountLocked(false);
			if (managerId != null) {
			    if (managerId == 0) {
			        u.setManager(null);
			    } else {
			        User manager = userService.getUserById(managerId);
			        u.setManager(manager);

			        
			        List<User> employees = manager.getEmployees();
			        if (employees == null) {
			            employees = new ArrayList<>();
			        }
			        employees.add(u);
			        manager.setEmployees(employees);
			    }
			}

	        
			u.setProfileImage(profileImage.getOriginalFilename());
			try {
				File saveFile = new File("src/main/resources/static/images/users");

				if (!saveFile.exists()) {
					saveFile.mkdirs();
				}

				Path path = Paths.get(saveFile.getAbsolutePath(), profileImage.getOriginalFilename());
				Files.copy(profileImage.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				u.setProfileImage(profileImage.getOriginalFilename());
				userService.addUser(u);
				MimeMessage mimeMessage = javaMailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.setFrom("sssurwade2212@gmail.com", "Eagles");
				helper.setTo(u.getEmail());
				helper.setSubject("Welcome to Eagles");

				String emailContent = "<div style=\"font-family: Arial, sans-serif; line-height: 1.6; color: #333; background-color: #f9f9f9; padding: 20px; border-radius: 8px; border: 1px solid #ddd;\">"
						+ "<div style=\"text-align: center; padding: 20px; background-color: #004aad; color: white; border-radius: 8px 8px 0 0;\">"
						+ "<h1 style=\"margin: 0; font-size: 24px;\">Welcome to Eagles!</h1>" + "</div>"
						+ "<div style=\"padding: 20px;\">"
						+ "<p style=\"font-size: 16px; margin: 0; color: #444;\">Dear <strong style=\"color: #004aad;\">"
						+ u.getName() + "</strong>,</p>"
						+ "<p style=\"font-size: 14px; margin: 10px 0; color: #555;\">Congratulations! You have been selected as a <strong style=\"color: #28a745;\">"
						+ u.getRole()
						+ "</strong> in our esteemed team <strong style=\"color: #004aad;\">Eagles</strong>.</p>"
						+ "<p style=\"font-size: 14px; margin: 10px 0; color: #555;\">Your journey with us begins on <strong style=\"color: #ff5722;\">"
						+ u.getJoinDate() + "</strong>. We are thrilled to have you on board!</p>"
						+ "<div style=\"margin: 20px 0; text-align: center;\">"
						+ "<a href=\"mailto:sssurwade2212@gmail.com\" style=\"display: inline-block; padding: 10px 20px; font-size: 14px; color: white; background-color: #004aad; text-decoration: none; border-radius: 4px;\">Contact Us</a>"
						+ "</div>"
						+ "<p style=\"font-size: 14px; margin: 10px 0; text-align: center; color: #777;\">We look forward to achieving great things together!</p>"
						+ "</div>"
						+ "<div style=\"text-align: center; padding: 10px; background-color: #f1f1f1; border-radius: 0 0 8px 8px; color: #888; font-size: 12px;\">"
						+ "<p style=\"margin: 0;\">&copy; 2024 Eagles Team. All rights reserved.</p>" + "</div>"
						+ "</div>";

				helper.setText(emailContent, true);

				javaMailSender.send(mimeMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:/";
		}
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
	
	@GetMapping("/all-users")
	public String getAllUsers(Model model) {
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String username = authentication.getName();
		User u = userRepository.findByEmail(username);
		model.addAttribute("user", u);
		model.addAttribute("users", userRepository.findAll());
		return "admins/all-users";
	}
}
