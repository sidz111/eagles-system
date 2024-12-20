package com.eagle.dto;

import java.util.List;

import com.eagle.entities.GroupChat;

public class GroupChatDTO {

	List<GroupChat> groupChats;

	public List<GroupChat> getGroupChats() {
		return groupChats;
	}

	public void setGroupChats(List<GroupChat> groupChats) {
		this.groupChats = groupChats;
	}
}
