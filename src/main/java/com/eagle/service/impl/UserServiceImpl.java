package com.eagle.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eagle.entities.Project;
import com.eagle.entities.User;
import com.eagle.entities.UserLogs;
import com.eagle.repository.ProjectRepository;
import com.eagle.repository.UserLogsRepository;
import com.eagle.repository.UserRepository;
import com.eagle.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
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
		if(user.isEmpty()) {
			return null;
		}
		else {
			return user.get();
		}
	}

	@Override
	public User getUserByEmail(String email) {
		Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
		if(user.isEmpty()) {
			return null;
		}
		else {
			return user.get();
		}
	}

	@Override
	public User addUser(User user) {
		List<Project> project = user.getProjects();
		List<UserLogs> userLogs = user.getUserLogs();
		for(Project p: project) {
			p.setUser(user);
		}
		for(UserLogs u: userLogs) {
			u.setUser(user);
		}
		projectRepository.saveAll(project);
		userLogsRepository.saveAll(userLogs);
		return this.userRepository.save(user);
	}

	@Override
	public User updateUser(User user) {
		Optional<User> u = userRepository.findById(user.getId());
		if(u.isEmpty()) {
			return null;
		}
		else {
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
		if(users.isEmpty()) {
			return null;
		}
		else {
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

}
