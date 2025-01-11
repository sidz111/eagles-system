package com.eagle.service;

import java.util.List;

import com.eagle.entities.User;

public interface UserService {
	List<User> getUsersByName(String name);
	List<User> getUsersByJoinDate(String joinDate);
	List<User> getUsersByDesignation(String designation);
	List<User> getUserByRole(String role);  // new method added
	List<User> getAllUsers();
	List<User> getEmployeesByManager(User manager); // new method added 2nd 
	List<User> getEmployeesByManagerId(Long id);   // new method 3rd
	User getUserById(Long id);
	User getUserByEmail(String email);
	Boolean isUserPresentEmail(String email);	// is user present by email or not
	
	User addUser(User user);
	User updateUser(User user);
	
	void deleteUserById(Long id);
	
	String generateOtp();
	
	void sendOtpToEmail(String email, String otp);
}
