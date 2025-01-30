package com.eagle.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.eagle.entities.Attendance;

public interface AttendanceService {

    // Save an attendance record
    Attendance saveAttendance(Attendance attendance);

    // Get all attendance records
    List<Attendance> getAllAttendance();

    // Get attendance by ID
    Optional<Attendance> getAttendanceById(Long id);

    // Get attendance records for a specific user
    List<Attendance> getAttendanceByUserId(Long userId);

    // Get attendance records for a specific user within a date range
    List<Attendance> getAttendanceByUserIdAndDate(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    // Update an existing attendance record
    Attendance updateAttendance(Long id, Attendance attendanceDetails);

    // Delete an attendance record by ID
    void deleteAttendance(Long id);

    // Check if a user was present on a specific date
    boolean isUserPresentOnDate(Long userId, LocalDateTime date);
}
