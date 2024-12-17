package com.eagle.dto;

import java.util.List;

import com.eagle.entities.Chatting;

public class ChattingDTO {
	private List<Chatting> chattings;

	public List<Chatting> getChattings() {
		return chattings;
	}

	public void setChattings(List<Chatting> chattings) {
		this.chattings = chattings;
	}
}
