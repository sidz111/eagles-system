package com.eagle.service;

import java.util.List;

import com.eagle.entities.Leave;

public interface LeaveService {

    // Apply for leave
    Leave applyLeave(Leave leave, Long userId);

    // Get all leave requests of a user
    List<Leave> getLeavesByUserId(Long userId);

    // Get all pending leaves (not approved)
    List<Leave> getPendingLeaves();

    // Get all approved leaves
    List<Leave> getApprovedLeaves();

    // Approve a leave request
    Leave approveLeave(Long leaveId);

    // Reject/Delete a leave request
    void deleteLeave(Long leaveId);
}
