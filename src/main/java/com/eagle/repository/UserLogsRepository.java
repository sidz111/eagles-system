package com.eagle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.entities.UserLogs;

@Repository
public interface UserLogsRepository extends JpaRepository<UserLogs, Long>{

	List<UserLogs> findByTimestamp(String timestamp);
}
