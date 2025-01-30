package com.eagle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.entities.Attendance;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Find all attendance records for a specific user
    List<Attendance> findByUserId(Long userId);

    // Find all attendance records for a specific user within a date range
    List<Attendance> findByUserIdAndDateTimeBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    // Find attendance records by presence status
    List<Attendance> findByIsPresent(Boolean isPresent);

    // Count total attendance records of a user
    Long countByUserId(Long userId);

    // Check if a user is present on a specific day
    Boolean existsByUserIdAndDateTimeBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}
