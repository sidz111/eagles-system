package com.eagle.service;

import java.util.List;

import com.eagle.entities.GroupChat;


public interface GroupChatService {
	GroupChat addChat(GroupChat groupChat);   //add
	GroupChat updateChat(GroupChat groupChat);  //update
	void deleteChat(Long id);
	
	List<GroupChat> getChatsByUserName(String Username);   //fidn by user name
	List<GroupChat> getChatsByUserId(Long userId);			// find by user id
	List<GroupChat> getAllChats();							// find all chats
	List<GroupChat> getChatsByUserIds(List<Long> userIds);

}
