package com.eagle.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eagle.entities.Chatting;
import com.eagle.entities.GroupChat;
import com.eagle.entities.Project;
import com.eagle.entities.User;
import com.eagle.repository.ChattingRepository;
import com.eagle.repository.GroupChatRepository;
import com.eagle.repository.UserLogsRepository;
import com.eagle.repository.UserRepository;
import com.eagle.service.ChattingService;
import com.eagle.service.GroupChatService;
import com.eagle.service.ProjectService;
import com.eagle.service.UserLogsService;
import com.eagle.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Controller
@RequestMapping("/manager")
public class ManagerController {
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	GroupChatRepository groupChatRepository;
	
	@Autowired
	GroupChatService groupChatService;

	@Autowired
	UserLogsService userLogsService;
	
	@Autowired
	ChattingService chattingService;
	
	@Autowired
	ChattingRepository chattingRepository;

	@Autowired
	UserLogsRepository userLogsRepository;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/assign-project")
	public String showAssignProjectForm() {
		return "managers/assign-project";
	}

	@PostMapping("/assign-project")
	public String assigningProject(@RequestParam("projectName") String projectName,
			@RequestParam("employeeId") Long employeeId,
			@RequestParam("description") String description,
			@RequestParam("expectedEndDate") String expectedEndDate,
			@RequestParam("status") String status,
			RedirectAttributes redirectAttributes) throws MessagingException, UnsupportedEncodingException {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User manager = userRepository.findByEmail(username);

		User emp = userService.getUserById(employeeId);
		if (emp == null) {
			redirectAttributes.addFlashAttribute("error", "Employee not found with ID: " + employeeId);
			return "redirect:/manager/assign-project";
		}
		Project p = new Project();
		p.setProjectName(projectName);
		p.setAssignBy(manager.getName());
		p.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		p.setDescription(description);
		p.setExpectedEndDate(expectedEndDate);
		p.setStatus(status);
		p.setEndDate(null);
		p.setRemark(null);
		p.setUser(emp);
		emp.getProjects().add(p);
		projectService.addProject(p);
		userService.updateUser(emp);
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("sssurwade2212@gmail.com", "Eagles");
		helper.setTo(emp.getEmail());
		helper.setSubject("Assigned New Project");

		String emailContent = "<div style=\"font-family: Arial, sans-serif; line-height: 1.6; color: #333; background-color: #f9f9f9; padding: 20px; border-radius: 8px; border: 1px solid #ddd;\">"
		        + "<div style=\"text-align: center; padding: 20px; background-color: #004aad; color: white; border-radius: 8px 8px 0 0;\">"
		        + "<h1 style=\"margin: 0; font-size: 24px;\">Welcome to Eagles!</h1>"
		        + "</div>"
		        + "<div style=\"padding: 20px;\">"
		        + "<p style=\"font-size: 16px; margin: 0; color: #444;\">Dear <strong style=\"color: #004aad;\">"
		        + emp.getName() + "</strong>,</p>"
		        + "<p style=\"font-size: 14px; margin: 10px 0; color: #555;\">Congratulations! You have been assigned a project as <strong style=\"color: #28a745;\">"
		        + p.getProjectName() + "</strong>.</p>"
		        + "<p style=\"font-size: 14px; margin: 10px 0; color: #555;\">Description: <strong style=\"color: #004aad;\">"
		        + p.getDescription() + "</strong></p>"
		        + "<p style=\"font-size: 14px; margin: 10px 0; color: #555;\">Expected End Date: <strong style=\"color: #004aad;\">"
		        + p.getExpectedEndDate() + "</strong></p>"
		        + "<p style=\"font-size: 16px; margin: 0; color: #444;\">By <strong style=\"color: #004aad;\">"
		        + manager.getName() + "</strong></p>"
		        + "<div style=\"margin: 20px 0; text-align: center;\">"
		        + "<a href=\"mailto:sssurwade2212@gmail.com\" style=\"display: inline-block; padding: 10px 20px; font-size: 14px; color: white; background-color: #004aad; text-decoration: none; border-radius: 4px;\">Contact Us</a>"
		        + "</div>"
		        + "<p style=\"font-size: 14px; margin: 10px 0; text-align: center; color: #777;\">We look forward to achieving great things together!</p>"
		        + "</div>"
		        + "<div style=\"text-align: center; padding: 10px; background-color: #f1f1f1; border-radius: 0 0 8px 8px; color: #888; font-size: 12px;\">"
		        + "<p style=\"margin: 0;\">&copy; 2024 Eagles Team. All rights reserved.</p>"
		        + "</div>"
		        + "</div>";

		helper.setText(emailContent, true);


		javaMailSender.send(mimeMessage);
		
		
		redirectAttributes.addFlashAttribute("success", "Project assigned successfully to " + emp.getName());
		return "redirect:/manager/assign-project";
	}

	@GetMapping("/submit-project/{projectId}/{remark}")
	public String updateProject(@PathVariable("projectId") Long id, @PathVariable("remark") Character remark,
			RedirectAttributes redirectAttributes) {
		Project project = projectService.getProjectById(id);
		if (project != null) {
			project.setRemark(remark);
			project.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			projectService.updateProject(project);
			redirectAttributes.addFlashAttribute("success", "Project updated successfully!");
		} else {
			redirectAttributes.addFlashAttribute("error", "Project not found or already completed!");
		}
		return "redirect:/all-projects";
	}

	@GetMapping("/my-team")
	public String getTeam(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User loggedUser = userRepository.findByEmail(username);
		List<User> employees = userRepository.findEmployeesByManager(loggedUser);
		model.addAttribute("users", employees);
		return "team";
	}

	@GetMapping("view-project")
	public String viewProject() {
		return "view-project";
	}
	
	@PostMapping("update-project")
	public String updateProject(@RequestParam("projectName") String projectName,
			@RequestParam("employeeId") Long employeeId,
			@RequestParam("description") String description,
			@RequestParam("expectedEndDate") String expectedEndDate,
			@RequestParam("status") String status,
			@RequestParam("pid") Long pid,
			RedirectAttributes redirectAttributes, Model model) {
		Project project = projectService.getProjectById(pid);
		if(project!=null) {
			model.addAttribute("project", project);
		}
		else {
			model.addAttribute("error", "Project Not found");
		}
		return "redirect:/view-project";
	}


	
}
