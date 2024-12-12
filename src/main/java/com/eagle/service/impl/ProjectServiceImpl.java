package com.eagle.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eagle.entities.Project;
import com.eagle.repository.ProjectRepository;
import com.eagle.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectRepository projectRepository;

	@Override
	public List<Project> getProjectsByProjectName(String projectName) {
		return this.projectRepository.findByProjectName(projectName);
	}

	@Override
	public List<Project> getProjectsByStartDate(String startDate) {
		return this.projectRepository.findByStartDate(startDate);
	}

	@Override
	public List<Project> getProjectsByEndDate(String endDate) {
		return this.projectRepository.findByEndDate(endDate);
	}

	@Override
	public List<Project> getProjectsByAssignBy(String assignBy) {
		return this.projectRepository.findByAssignBy(assignBy);
	}

	@Override
	public List<Project> getProjectsByRemark(Character remark) {
		return this.projectRepository.findByRemark(remark);
	}

	@Override
	public List<Project> getAllProjects() {
		return this.projectRepository.findAll();
	}

	@Override
	public Project getProjectById(Long id) {
		Optional<Project> project = projectRepository.findById(id);
		if (project.isEmpty()) {
			return null;
		} else {
			return project.get();
		}
	}

	@Override
	public Project updateProject(Project project) {
		Optional<Project> p = projectRepository.findById(project.getId());
		if (p.isEmpty()) {
			return null;
		} else {
			return projectRepository.save(project);
		}
	}

	@Override
	public void deleteProjectById(Long id) {
		projectRepository.deleteById(id);
	}

	@Override
	public Project addProject(Project project) {
		return projectRepository.save(project);
	}
}