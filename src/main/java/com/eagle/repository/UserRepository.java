package com.eagle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.entities.Chatting;
import com.eagle.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
	List<User> findByName(String name);
	List<User> findByJoinDate(String joinDate);
	List<User> findByDesignation(String designation);
	Chatting save(Optional<Chatting> chat);
}
