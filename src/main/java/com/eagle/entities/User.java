package com.eagle.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long randomId;
	private String name;
	private String email;
	private String password;
	private String contact;
	private String role;
	private String joinDate;
	private String designation;
	private String profileImage;
	private Boolean isAccountLocked;
	private Integer otp;

	@OneToMany(mappedBy = "user_project", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Project> projects = new ArrayList<Project>();

	@OneToMany(mappedBy = "user_logs", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserLogs> userLogs = new ArrayList<UserLogs>();

	@OneToMany(mappedBy = "user_notifications", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Notifications> notifications;

	@OneToMany(mappedBy = "user_chat", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Chatting> chattings = new ArrayList<>();

	@OneToMany(mappedBy = "user_chat", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GroupChat> Group_chatting = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "manager_id")
	private User manager;

	@OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<User> employees = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Attendance> attendanceRecords = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Leave> leaves = new ArrayList<>();

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

	public Long getRandomId() {
		return randomId;
	}

	public void setRandomId(Long randomId) {
		this.randomId = randomId;
	}

	public List<Notifications> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notifications> notifications) {
		this.notifications = notifications;
	}

	public List<Chatting> getChattings() {
		return chattings;
	}

	public void setChattings(List<Chatting> chattings) {
		this.chattings = chattings;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public List<User> getEmployees() {
		return employees;
	}

	public void setEmployees(List<User> employees) {
		this.employees = employees;
	}

	public List<GroupChat> getGroup_chatting() {
		return Group_chatting;
	}

	public void setGroup_chatting(List<GroupChat> group_chatting) {
		Group_chatting = group_chatting;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public List<Attendance> getAttendanceRecords() {
		return attendanceRecords;
	}

	public void setAttendanceRecords(List<Attendance> attendanceRecords) {
		this.attendanceRecords = attendanceRecords;
	}

	public List<Leave> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<Leave> leaves) {
		this.leaves = leaves;
	}
}