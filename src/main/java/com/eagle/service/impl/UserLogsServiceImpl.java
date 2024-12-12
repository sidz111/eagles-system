package com.eagle.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eagle.entities.UserLogs;
import com.eagle.repository.UserLogsRepository;
import com.eagle.service.UserLogsService;

@Service
public class UserLogsServiceImpl implements UserLogsService{
	
	@Autowired
	UserLogsRepository userLogsRepository;

	@Override
	public List<UserLogs> getUserLogsByTimestamp(String timestamp) {
		return this.userLogsRepository.findByTimestamp(timestamp);
	}

	@Override
	public List<UserLogs> getAllUserLogs() {
		return this.userLogsRepository.findAll();
	}

	@Override
	public UserLogs getUserLogsById(Long id) {
		Optional<UserLogs> userLogs = userLogsRepository.findById(id);
		if(userLogs.isEmpty()) {
			return null;
		}
		else {
			return userLogs.get();
		}
	}

	@Override
	public UserLogs addUserLogs(UserLogs userLogs) {
		return this.userLogsRepository.save(userLogs);
	}

	@Override
	public UserLogs updateUserLogs(UserLogs userLogs) {
		Optional<UserLogs> u = userLogsRepository.findById(userLogs.getId());
		if(u.isEmpty()) {
			return null;
		}
		else {
			return userLogsRepository.save(userLogs);
		}
	}

	@Override
	public void deleteUserLogsById(Long id) {
		userLogsRepository.deleteById(id);
	}

}
