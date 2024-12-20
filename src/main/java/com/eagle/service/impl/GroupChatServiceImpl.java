package com.eagle.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eagle.entities.GroupChat;
import com.eagle.entities.User;
import com.eagle.repository.GroupChatRepository;
import com.eagle.repository.UserRepository;
import com.eagle.service.GroupChatService;

@Service
public class GroupChatServiceImpl implements GroupChatService {
	
	@Autowired
	private GroupChatRepository groupChatRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public GroupChat addChat(GroupChat groupChat) {
		if(groupChat != null) {
			// Ensure the user is set correctly in the GroupChat entity
			User user = userRepository.findById(groupChat.getUser_chat().getId()).orElse(null);
			if (user == null) {
				// Handle user not found case
				return null;
			}
			
			groupChat.setUser_chat(user);
			return groupChatRepository.save(groupChat);
		}
		return null;
	}

	@Override
	public GroupChat updateChat(GroupChat groupChat) {
		Optional<GroupChat> chat = groupChatRepository.findById(groupChat.getId());
		if (chat.isEmpty()) {
			return null;
		} else {
			return groupChatRepository.save(groupChat);
		}
	}

	@Override
	public void deleteChat(Long id) {
		groupChatRepository.deleteById(id);
	}

	@Override
	public List<GroupChat> getChatsByUserName(String username) {
		List<GroupChat> chats = groupChatRepository.findAllChatsByUserName(username);
		if (chats.isEmpty()) {
			return null;
		}
		return chats;
	}

	@Override
	public List<GroupChat> getChatsByUserId(Long userId) {
		List<GroupChat> chats = groupChatRepository.findAllChatsByUserId(userId);
		if (chats.isEmpty()) {
			return null;
		}
		return chats;
	}

	@Override
	public List<GroupChat> getAllChats() {
		return groupChatRepository.findAll();
	}
	
	@Override
	public List<GroupChat> getChatsByUserIds(List<Long> userIds) {
	    return groupChatRepository.findAllChatsByUserIds(userIds);
	}

}
