package com.eagle.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;
	private String contact;
	private String role;
	private String joinDate;
	private String designation;
	private String profileImage;
	private Boolean isAccountLocked;

	@OneToMany(mappedBy = "user_project", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Project> projects = new ArrayList<Project>();

	@OneToMany(mappedBy = "user_logs", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserLogs> userLogs = new ArrayList<UserLogs>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public Boolean getIsAccountLocked() {
		return isAccountLocked;
	}

	public void setIsAccountLocked(Boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<UserLogs> getUserLogs() {
		return userLogs;
	}

	public void setUserLogs(List<UserLogs> userLogs) {
		this.userLogs = userLogs;
	}

	public User(Long id, String name, String email, String password, String contact, String role, String joinDate,
			String designation, String profileImage, Boolean isAccountLocked, List<Project> projects,
			List<UserLogs> userLogs) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.contact = contact;
		this.role = role;
		this.joinDate = joinDate;
		this.designation = designation;
		this.profileImage = profileImage;
		this.isAccountLocked = isAccountLocked;
		this.projects = projects;
		this.userLogs = userLogs;
	}
	public User() {
		super();
	}
}