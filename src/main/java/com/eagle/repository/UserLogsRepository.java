package com.eagle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.entities.UserLogs;

@Repository
public interface UserLogsRepository extends JpaRepository<UserLogs, Long>{

	List<UserLogs> findByTimestamp(String timestamp);
	@Query("SELECT ul FROM UserLogs ul WHERE ul.user_logs.id = :userId")
    List<UserLogs> findByUserId(@Param("userId") Long userId);
}