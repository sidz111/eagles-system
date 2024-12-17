package com.eagle.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eagle.entities.Chatting;
import com.eagle.entities.Notifications;
import com.eagle.entities.User;
import com.eagle.entities.UserLogs;
import com.eagle.repository.ChattingRepository;
import com.eagle.repository.UserLogsRepository;
import com.eagle.repository.UserRepository;
import com.eagle.service.ChattingService;
import com.eagle.service.NotificationsService;
import com.eagle.service.ProjectService;
import com.eagle.service.UserLogsService;
import com.eagle.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	UserLogsRepository userLogsRepository;
	
	@Autowired
	ChattingService chattingService;
	
	@Autowired
	ChattingRepository chattingRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserLogsService userLogsService;

	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	NotificationsService notificationsService;

	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("users", userRepository.findAll());
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String username = authentication.getName();
		User u = userRepository.findByEmail(username);
		model.addAttribute("user", u);
		model.addAttribute("notifications_auth", u.getNotifications());
		model.addAttribute("notifications", notificationsService.getAllNotifications());
		return "index";
	}

	@GetMapping("/all-users")
	public String getAllUsers(Model model) {
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String username = authentication.getName();
		User u = userRepository.findByEmail(username);
		model.addAttribute("user", u);
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
	public String addLogPost(@RequestParam("logDescription") String logDescription,
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
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByEmail(username);
		model.addAttribute("user", user);
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

	@GetMapping("/view-user/{id}")
    	public String viewUser(@PathVariable Long id, Model model) {
		User u = userService.getUserById(id);
		if(u==null) {
			model.addAttribute("error", "User not found with id: "+id);
			return "redirect:/all-users";
		}
		else {
		model.addAttribute("user", u);
		return "view-user";
		}
	}
	
	@GetMapping("/add-notification")
	public String getNotification() {
		return "add-notification";
	}
	
	@PostMapping("/add-notification")
	public String addNotification(@RequestParam("message") String message,
			RedirectAttributes redirectAttributes
			) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username);
        Notifications notifications = new Notifications();
        notifications.setMessage(message);
        notifications.setUser_notifications(user);
        notifications.setDate(new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date()));
        notificationsService.addNotifications(notifications);
		return "redirect:/";
	}
	
	@GetMapping("/remove-notification/{id}")
	public String removeNotification(@PathVariable("id") Long id) {
	    notificationsService.deleteNotification(id);
	    return "redirect:/";
	}
	
	@GetMapping("/chatt")
	public String getChattings(Model model) {
		model.addAttribute("chatts", chattingService.getAllChats());
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByEmail(username);
		model.addAttribute("user", user);
		return "chatt";
	}

	@PostMapping("/chatt")
	public String addChatts(@RequestParam("message") String message, RedirectAttributes redirectAttributes) {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    User user = userRepository.findByEmail(username);
	    Chatting chatting = new Chatting();
	    chatting.setMessage(message);
	    chatting.setUser_chat(user);
	    chatting.setTime(new SimpleDateFormat().format(new Date()));
	    chattingRepository.save(chatting);
	    return "redirect:/chatt";
	}

}
