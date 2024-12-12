package com.eagle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.entities.Project;
import com.eagle.entities.UserLogs;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{
	
	List<Project> findByStartDate(String startDate);
	List<Project> findByEndDate(String endDate);
	List<Project> findByAssignBy(String assignBy);
	List<Project> findByProjectName(String projectName);
	List<Project> findByRemark(Character remark);
	
	@Query("SELECT ul FROM Project ul WHERE ul.user_project.id = :userId")
    List<Project> findByUserId(@Param("userId") Long userId);

}
