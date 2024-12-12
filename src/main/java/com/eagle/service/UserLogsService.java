package com.eagle.service;

import java.util.List;

import com.eagle.entities.UserLogs;

public interface UserLogsService {
	List<UserLogs> getUserLogsByTimestamp(String timestamp);
	List<UserLogs> getAllUserLogs();
	UserLogs getUserLogsById(Long id);
	
	UserLogs addUserLogs(UserLogs userLogs);
	
	UserLogs updateUserLogs(UserLogs userLogs);
	
	void deleteUserLogsById(Long id);
}
