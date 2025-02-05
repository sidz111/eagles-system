package com.eagle.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Leaves")
public class Leave {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String leaveFrom;
	private String leaveTo;
	private String reason;
	private Boolean isPermit = false; // Default false

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Leave() {
	}

	public Leave(String leaveFrom, String leaveTo, String reason, User user) {
		this.leaveFrom = leaveFrom;
		this.leaveTo = leaveTo;
		this.reason = reason;
		this.user = user;
		this.isPermit = false; // By default, leave is not permitted
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLeaveFrom() {
		return leaveFrom;
	}

	public void setLeaveFrom(String leaveFrom) {
		this.leaveFrom = leaveFrom;
	}

	public String getLeaveTo() {
		return leaveTo;
	}

	public void setLeaveTo(String leaveTo) {
		this.leaveTo = leaveTo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Boolean getIsPermit() {
		return isPermit;
	}

	public void setIsPermit(Boolean isPermit) {
		this.isPermit = isPermit;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
