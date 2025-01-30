package com.eagle.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.eagle.entities.Project;
import com.eagle.entities.User;
import com.eagle.entities.UserLogs;
import com.eagle.repository.ProjectRepository;
import com.eagle.repository.UserLogsRepository;
import com.eagle.repository.UserRepository;
import com.eagle.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	UserLogsRepository userLogsRepository;

	@Override
	public List<User> getUsersByName(String name) {
		return this.userRepository.findByName(name);
	}

	@Override
	public List<User> getUsersByJoinDate(String joinDate) {
		return this.userRepository.findByJoinDate(joinDate);
	}

	@Override
	public List<User> getUsersByDesignation(String designation) {
		return this.userRepository.findByDesignation(designation);
	}

	@Override
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}

	@Override
	public User getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			return null;
		} else {
			return user.get();
		}
	}

	@Override
	public User getUserByEmail(String email) {
		Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
		if (user.isEmpty()) {
			return null;
		} else {
			return user.get();
		}
	}

	@Override
	public User addUser(User user) {
		List<Project> project = user.getProjects();
		List<UserLogs> userLogs = user.getUserLogs();
		for (Project p : project) {
			p.setUser(user);
		}
		for (UserLogs u : userLogs) {
			u.setUser(user);
		}
		projectRepository.saveAll(project);
		userLogsRepository.saveAll(userLogs);
		return this.userRepository.save(user);
	}

	@Override
	public User updateUser(User user) {
		Optional<User> u = userRepository.findById(user.getId());
		if (u.isEmpty()) {
			return null;
		} else {
			return userRepository.save(user);
		}
	}

	@Override
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public List<User> getUserByRole(String role) {
		List<User> users = userRepository.findByRole(role);
		if (users.isEmpty()) {
			return null;
		} else {
			return users;
		}
	}

	@Override
	public List<User> getEmployeesByManager(User manager) {
		return userRepository.findEmployeesByManager(manager);
	}

	@Override
	public List<User> getEmployeesByManagerId(Long id) {
		return this.userRepository.findEmployeesByManagerId(id);
	}

	@Override
	public Boolean isUserPresentEmail(String email) {
		User u = userRepository.findByEmail(email);
		if (u.getId() == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String generateOtp() {
		Random random = new Random();
		int otp = random.nextInt(900000) + 100000;
		return String.valueOf(otp);
	}

//    @Override
//    public void sendOtpToEmail(String email, String otp) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("sssurwade2212@gmail.com");
//        message.setTo(email);
//        message.setSubject("Your OTP Code");
//        message.setText("Your OTP is: " + otp);
//        mailSender.send(message);
//    }

	@Override
	public void sendOtpToEmail(String email, String otp) {
		try {
			// Create a MimeMessage
			MimeMessage mimeMessage = mailSender.createMimeMessage();

			// Use MimeMessageHelper to configure the message
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // 'true' enables multipart mode
			helper.setFrom("sssurwade2212@gmail.com");
			helper.setTo(email);
			helper.setSubject("Your OTP Code");

			helper.setText("<html>"
					+ "<body style=\"font-family: Arial, sans-serif; text-align: center; background-color: #f9f9f9; padding: 20px;\">"
					+ "<div style=\"max-width: 600px; margin: auto; background: #ffffff; padding: 20px; border-radius: 10px; "
					+ "box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);\">"
					+ "<h2 style=\"color: #4CAF50;\">Your OTP Code</h2>" + "<p style=\"font-size: 16px;\">Hello,</p>"
					+ "<p style=\"font-size: 16px;\">Your One-Time Password (OTP) is:</p>"
					+ "<h3 style=\"color: #333;\">" + otp + "</h3>"
					+ "<p style=\"font-size: 14px; color: #555;\">Please use this code to complete your verification. "
					+ "This code is valid for the next 10 minutes.</p>"
					+ "<hr style=\"border: none; border-top: 1px solid #ddd;\">"
					+ "<p style=\"font-size: 12px; color: #777;\">If you didn’t request this code, you can safely ignore this email.</p>"
					+ "</div>" + "</body>" + "</html>", true);

//			helper.setText(htmlContent, true);

			mailSender.send(mimeMessage);

		} catch (MessagingException e) {
			e.printStackTrace(); // Log the error (replace with proper logging in production)
			throw new RuntimeException("Failed to send email: " + e.getMessage());
		}
	}

}
