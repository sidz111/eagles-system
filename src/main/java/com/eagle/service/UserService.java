package com.eagle.service;

import java.util.List;

import com.eagle.entities.User;

public interface UserService {
	List<User> getUsersByName(String name);
	List<User> getUsersByJoinDate(String joinDate);
	List<User> getUsersByDesignation(String designation);
	List<User> getAllUsers();
	
	User getUserById(Long id);
	User getUserByEmail(String email);
	
	User addUser(User user);
	User updateUser(User user);
	
	void deleteUserById(Long id);
}
