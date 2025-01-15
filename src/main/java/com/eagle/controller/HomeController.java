package com.eagle.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
import com.eagle.service.impl.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Controller
public class HomeController {
	
	@Autowired
    private MailService mailService;

	@Autowired
	PasswordEncoder passwordEncoder;

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

	@Autowired
	JavaMailSender javaMailSender;

	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("users", userRepository.findAll());
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String username = authentication.getName();
		User u = userRepository.findByEmail(username);
		model.addAttribute("user", u);
		model.addAttribute("notifications_auth", u.getNotifications());
		List<Notifications> notificationsList = notificationsService.getAllNotifications();
		Collections.reverse(notificationsList);
		model.addAttribute("notifications", notificationsList);
//		model.addAttribute("user", new User());
		model.addAttribute("managers", userService.getUserByRole("ROLE_MANAGER"));
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
	public String addNotification(@RequestParam("message") String message, RedirectAttributes redirectAttributes) {

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

	@GetMapping("/reset")
	public String getResetPage() {
		return "reset";
	}

	@GetMapping("/new-pass")
	public String getNewPass() {
		return "new-pass";
	}

	@PostMapping("/forget-pass")
	public String forgetPassword(@RequestParam("email") String email, Model model)
			throws MessagingException, UnsupportedEncodingException {
		Optional<User> u = Optional.ofNullable(userService.getUserByEmail(email));
		if (u.isEmpty()) {
			model.addAttribute("message", "User Not found with " + email + " email");
			return "reset";
		} else {
			String otp = userService.generateOtp();
			u.get().setOtp(Integer.parseInt(otp));
			userService.addUser(u.get());
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setFrom("sssurwade2212@gmail.com", "Eagles");
			helper.setTo(email);
			helper.setSubject("OTP for Forget Password");

			String emailContent = u.get().getOtp().toString();
			helper.setText(emailContent);

			javaMailSender.send(mimeMessage);
			model.addAttribute("message", "OTP sent to your email.");
			model.addAttribute("email", email);
			return "reset";
		}
	}

	@PostMapping("/reset-password")
	public String resetPassword(Model model, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("otp") Integer otpFromUser,
			RedirectAttributes redirectAttributes) throws UnsupportedEncodingException, MessagingException {

		Optional<User> userOpt = Optional.ofNullable(userService.getUserByEmail(email));
		if (userOpt.isEmpty()) {
			model.addAttribute("emailError", "No account found with this email.");
			return "reset";
		}

		User u = userOpt.get();

		if (!otpFromUser.equals(u.getOtp())) {
			model.addAttribute("otpError", "Invalid OTP.");
			return "reset";
		}

		u.setPassword(passwordEncoder.encode(password));
		userService.addUser(u);

		u.setOtp(null);
		userService.addUser(u);

		redirectAttributes.addFlashAttribute("message", "Password successfully reset.");
		return "redirect:/signin";
	}
	
	    @GetMapping("/mail")
	    public String indexPage() {
	        return "mail";
	    }

	    @PostMapping("/mail")
	    public String sendMail(Model model, 
	                           @RequestParam String to,
	                           @RequestParam String subject,
	                           @RequestParam String msg,
	                           @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
	        try {
	            mailService.mailSending(to, msg, subject, file);
	            model.addAttribute("success", "Mail sent successfully to: " + to);
	        } catch (MessagingException | IllegalStateException e) {
	            model.addAttribute("error", "Error while sending mail: " + e.getMessage());
	        }
	        return "success-page";
	    }

}
