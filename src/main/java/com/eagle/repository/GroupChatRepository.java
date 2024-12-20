package com.eagle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.entities.GroupChat;

@Repository
public interface GroupChatRepository extends JpaRepository<GroupChat, Long>{

	@Query("SELECT c FROM GroupChat c WHERE c.user_chat.name = :name")
	List<GroupChat> findAllChatsByUserName(@Param("name") String name);

	@Query("SELECT c FROM GroupChat c WHERE c.user_chat.id = :id")
	List<GroupChat> findAllChatsByUserId(@Param("id") Long id);
	
	@Query("SELECT c FROM GroupChat c WHERE c.user_chat.id IN :ids")
	List<GroupChat> findAllChatsByUserIds(@Param("ids") List<Long> ids);

}
