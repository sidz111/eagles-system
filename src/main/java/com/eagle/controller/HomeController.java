package com.eagle.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.eagle.entities.GroupChat;
import com.eagle.entities.Notifications;
import com.eagle.entities.User;
import com.eagle.entities.UserLogs;
import com.eagle.repository.ChattingRepository;
import com.eagle.repository.GroupChatRepository;
import com.eagle.repository.UserLogsRepository;
import com.eagle.repository.UserRepository;
import com.eagle.service.ChattingService;
import com.eagle.service.GroupChatService;
import com.eagle.service.NotificationsService;
import com.eagle.service.ProjectService;
import com.eagle.service.UserLogsService;
import com.eagle.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	GroupChatService groupChatService;
	
	@Autowired
	GroupChatRepository groupChatRepository;
	
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

//	@GetMapping("/view-user/{id}")
//    	public String viewUser(@PathVariable Long id, Model model) {
//		User u = userService.getUserById(id);
//		if(u==null) {
//			model.addAttribute("error", "User not found with id: "+id);
//			return "redirect:/all-users";
//		}
//		else {
//		model.addAttribute("user", u);
//		return "view-user";
//		}
//	}
	@GetMapping("/view-user/{id}")
	public String viewUser(@PathVariable Long id, Model model) {
		User u = userService.getUserById(id);
		if (u == null) {
			model.addAttribute("error", "User not found with id: " + id);
			return "redirect:/all-users";
		} else {
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
	
	@GetMapping("/meeting")
	public String getMeeting(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.getUserByEmail(username);
		model.addAttribute("user", u);
		return "meeting";
	}
	
	
//	@GetMapping("/group-chat")
//	public String getGroupChat(Model model) {
//	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
//	    User currentUser = userService.getUserByEmail(username);
//	    List<User> allEmployees = new ArrayList<>();
//
//	    if (currentUser.getManager() != null) {
//	        Long managerId = currentUser.getManager().getId();
//	        allEmployees = userService.getEmployeesByManagerId(managerId);
//	        User manager = userService.getUserById(managerId);
//	        allEmployees.add(manager);
//	    } else {
//	        allEmployees = userService.getEmployeesByManagerId(currentUser.getId());
//	        allEmployees.add(currentUser);
//	    }
//	    List<GroupChat> chats = groupChatRepository.findAll();
//	    List<GroupChat> allChats = groupChatRepository.findAllChatsByUserId(currentUser.getId());    // working in progress
//	    
//	    model.addAttribute("participants", allEmployees);
//	    model.addAttribute("chatts", chats);
//	    model.addAttribute("user", currentUser);
//
//	    return "group-chat";
//	}
	
	@GetMapping("/group-chat")
	public String getGroupChat(Model model) {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    User currentUser = userService.getUserByEmail(username);
	    
	    List<User> allEmployees = new ArrayList<>();
	    if (currentUser.getManager() != null) {
	        Long managerId = currentUser.getManager().getId();
	        allEmployees = userService.getEmployeesByManagerId(managerId);
	        User manager = userService.getUserById(managerId);
	        allEmployees.add(manager);
	    } else {
	        allEmployees = userService.getEmployeesByManagerId(currentUser.getId());
	        allEmployees.add(currentUser);
	    }

	    List<Long> userIds = allEmployees.stream().map(User::getId).toList();

	    List<GroupChat> relatedChats = groupChatService.getChatsByUserIds(userIds);

	    model.addAttribute("participants", allEmployees);
	    model.addAttribute("chatts", relatedChats);
	    model.addAttribute("user", currentUser);
	    model.addAttribute("manager", currentUser.getManager());

	    return "group-chat";
	}

	
	@PostMapping("/group-chat")
	public String addGroupChat(@RequestParam("message") String message, RedirectAttributes redirectAttributes) {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    User user = userRepository.findByEmail(username);
	    
	    if (user == null) {
	        redirectAttributes.addFlashAttribute("error", "User not found.");
	        return "redirect:/group-chat";
	    }

	    GroupChat groupChat = new GroupChat();
	    groupChat.setMessage(message);
	    groupChat.setUser_chat(user);
	    groupChat.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));

	    groupChatRepository.save(groupChat);

	    return "redirect:/group-chat"; 
	}
}
