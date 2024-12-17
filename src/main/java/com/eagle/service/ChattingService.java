package com.eagle.service;

import java.util.List;

import com.eagle.entities.Chatting;

public interface ChattingService {
	Chatting addChat(Chatting chatting);   //add
	Chatting updateChat(Chatting chatting);  //update
	void deleteChat(Long id);
	
	List<Chatting> getChatsByUserName(String Username);   //fidn by user name
	List<Chatting> getChatsByUserId(Long userId);			// find by user id
	List<Chatting> getAllChats();							// find all chats
	
}
