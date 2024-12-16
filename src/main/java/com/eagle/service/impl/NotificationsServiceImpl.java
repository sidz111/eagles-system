package com.eagle.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eagle.entities.Notifications;
import com.eagle.repository.NotificationsRepository;
import com.eagle.service.NotificationsService;

@Service
public class NotificationsServiceImpl implements NotificationsService{
	
	@Autowired
	NotificationsRepository notificationsRepository;

	@Override
	public Notifications getNotificationById(Long id) {
		Optional<Notifications> notification = notificationsRepository.findById(id);
		if(notification.isEmpty()) {
			return null;
		}
		else {
			return notification.get();
		}
	}

	@Override
	public Notifications updateNotificationsById(Notifications notifications) {
		Optional<Notifications> notification = notificationsRepository.findById(notifications.getId());
		if(notification.isEmpty()) {
			return null;
		}
		else {
			return notificationsRepository.save(notifications);
		}
	}

	@Override
	public void deleteNotification(Long id) {
		notificationsRepository.deleteById(id);		
	}

	@Override
	public List<Notifications> getAllNotifications() {
		return this.notificationsRepository.findAll();
	}

	@Override
	public Notifications addNotifications(Notifications notifications) {
		if(notifications != null) {
			return notificationsRepository.save(notifications);
		}
		else {
			return null;
		}
	}

}
