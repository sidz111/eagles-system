package com.eagle.service;

import java.util.List;

import com.eagle.entities.Project;

public interface ProjectService {
	List<Project> getProjectsByProjectName(String projectName);
	List<Project> getProjectsByStartDate(String startDate);
	List<Project> getProjectsByEndDate(String endDate);
	List<Project> getProjectsByAssignBy(String assignBy);
	List<Project> getProjectsByRemark(Character remark);
	List<Project> getAllProjects();
	
	Project getProjectById(Long id);
	
	Project addProject(Project project);
	
	Project updateProject(Project project);
	
	void deleteProjectById(Long id);
}
