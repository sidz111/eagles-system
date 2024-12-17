package com.eagle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.entities.Chatting;

@Repository
public interface ChattingRepository extends JpaRepository<Chatting, Long> {
	@Query("SELECT c FROM Chatting c WHERE c.user_chat.name = :name")
	List<Chatting> findAllChatsByUserName(@Param("name") String name);

	@Query("SELECT c FROM Chatting c WHERE c.user_chat.id = :id")
	List<Chatting> findAllChatsByUserId(@Param("id") Long id);

}
