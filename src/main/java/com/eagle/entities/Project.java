package com.eagle.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String projectName;

	private String startDate;

	private String endDate;

	private String assignBy;

	private Character remark;
	
	private List<String> suggesions;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user_project;

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public User getUser() {
		return user_project;
	}

	public void setUser(User user) {
		this.user_project = user;
	}

	public String getAssignBy() {
		return assignBy;
	}

	public void setAssignBy(String assignBy) {
		this.assignBy = assignBy;
	}

	public Character getRemark() {
		return remark;
	}

	public void setRemark(Character remark) {
		this.remark = remark;
	}

	public List<String> getSuggesions() {
		return suggesions;
	}

	public void setSuggesions(List<String> suggesions) {
		this.suggesions = suggesions;
	}

	public User getUser_project() {
		return user_project;
	}

	public void setUser_project(User user_project) {
		this.user_project = user_project;
	}
}
