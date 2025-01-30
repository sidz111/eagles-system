package com.eagle.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eagle.entities.Attendance;
import com.eagle.repository.AttendanceRepository;
import com.eagle.service.AttendanceService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public Attendance saveAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    @Override
    public Optional<Attendance> getAttendanceById(Long id) {
        return attendanceRepository.findById(id);
    }

    @Override
    public List<Attendance> getAttendanceByUserId(Long userId) {
        return attendanceRepository.findByUserId(userId);
    }

    @Override
    public List<Attendance> getAttendanceByUserIdAndDate(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return attendanceRepository.findByUserIdAndDateTimeBetween(userId, startDate, endDate);
    }

    @Override
    public Attendance updateAttendance(Long id, Attendance attendanceDetails) {
        Optional<Attendance> optionalAttendance = attendanceRepository.findById(id);
        if (optionalAttendance.isPresent()) {
            Attendance attendance = optionalAttendance.get();
            attendance.setCurrentLocation(attendanceDetails.getCurrentLocation());
            attendance.setDateTime(attendanceDetails.getDateTime());
            attendance.setIsPresent(attendanceDetails.getIsPresent());
            attendance.setLatitude(attendanceDetails.getLatitude());
            attendance.setLongitude(attendanceDetails.getLongitude());
            return attendanceRepository.save(attendance);
        }
        throw new RuntimeException("Attendance not found with ID: " + id);
    }

    @Override
    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

    @Override
    public boolean isUserPresentOnDate(Long userId, LocalDateTime date) {
        List<Attendance> attendanceRecords = attendanceRepository.findByUserIdAndDateTimeBetween(
                userId, date.withHour(0).withMinute(0).withSecond(0), 
                date.withHour(23).withMinute(59).withSecond(59)
        );
        return attendanceRecords.stream().anyMatch(Attendance::getIsPresent);
    }
}
