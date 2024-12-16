package com.eagle.service;

import java.util.List;

import com.eagle.entities.Notifications;

public interface NotificationsService {

	Notifications getNotificationById(Long id);
	Notifications updateNotificationsById(Notifications notifications);
	void deleteNotification(Long id);
	
	List<Notifications> getAllNotifications();
	
	Notifications addNotifications(Notifications notifications);
}
