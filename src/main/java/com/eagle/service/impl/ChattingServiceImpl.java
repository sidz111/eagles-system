package com.eagle.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eagle.entities.Chatting;
import com.eagle.entities.User;
import com.eagle.repository.ChattingRepository;
import com.eagle.repository.UserRepository;
import com.eagle.service.ChattingService;

@Service
public class ChattingServiceImpl implements ChattingService{
	
	@Autowired
	private ChattingRepository chattingRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Chatting addChat(Chatting chatting) {
		if(chatting != null) {
			Chatting chat = new Chatting();
			User u = chat.getUser_chat();
			userRepository.save(u);
			return chattingRepository.save(chatting);
		}else {
			return null;
		}
		
	}

	@Override
	public Chatting updateChat(Chatting chatting) {
		Optional<Chatting> chat = chattingRepository.findById(chatting.getId());
		if(chat.isEmpty()) {
			return null;
		}
		else {
			return userRepository.save(chat);
		}
	}

	@Override
	public void deleteChat(Long id) {
		chattingRepository.deleteById(id);
	}

	@Override
	public List<Chatting> getChatsByUserName(String Username) {
		List<Chatting> chats = chattingRepository.findAllChatsByUserName(Username);
		if(chats.isEmpty()) {
			return null;
		}
		else {
			return chats;
		}
	}

	@Override
	public List<Chatting> getChatsByUserId(Long userId) {
		List<Chatting> chats = chattingRepository.findAllChatsByUserId(userId);
		if(chats.isEmpty()) {
			return null;
		}
		else {
			return chats;
		}
	}

	@Override
	public List<Chatting> getAllChats() {
		return this.chattingRepository.findAll();
	}

}
