package com.eagle.service.impl;

import com.eagle.entities.Leave;
import com.eagle.entities.User;
import com.eagle.repository.LeaveRepository;
import com.eagle.repository.UserRepository;
import com.eagle.service.LeaveService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Leave applyLeave(Leave leave, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            leave.setUser(userOptional.get());
            leave.setIsPermit(false); // Default to not approved
            return leaveRepository.save(leave);
        }
        throw new RuntimeException("User not found with ID: " + userId);
    }

    @Override
    public List<Leave> getLeavesByUserId(Long userId) {
        return leaveRepository.findByUserId(userId);
    }

    @Override
    public List<Leave> getPendingLeaves() {
        return leaveRepository.findByIsPermitFalse();
    }

    @Override
    public List<Leave> getApprovedLeaves() {
        return leaveRepository.findByIsPermitTrue();
    }

    @Override
    public Leave approveLeave(Long leaveId) {
        Optional<Leave> leaveOptional = leaveRepository.findById(leaveId);
        if (leaveOptional.isPresent()) {
            Leave leave = leaveOptional.get();
            leave.setIsPermit(true);
            return leaveRepository.save(leave);
        }
        throw new RuntimeException("Leave request not found with ID: " + leaveId);
    }

    @Override
    public void deleteLeave(Long leaveId) {
        if (!leaveRepository.existsById(leaveId)) {
            throw new RuntimeException("Leave request not found with ID: " + leaveId);
        }
        leaveRepository.deleteById(leaveId);
    }
}
