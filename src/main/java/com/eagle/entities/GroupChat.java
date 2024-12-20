package com.eagle.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class GroupChat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String message;
	
	private String time;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user_chat;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser_chat() {
		return user_chat;
	}

	public void setUser_chat(User user_chat) {
		this.user_chat = user_chat;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public GroupChat(String message, String time, User user_chat) {
		super();
		this.message = message;
		this.time = time;
		this.user_chat = user_chat;
	}

	public GroupChat() {
		super();
	}
}
