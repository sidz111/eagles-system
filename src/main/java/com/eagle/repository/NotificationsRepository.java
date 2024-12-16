package com.eagle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.entities.Notifications;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Long>{

}
