package com.eagle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.entities.Leave;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    // Find all leaves of a specific user
    List<Leave> findByUserId(Long userId);

    // Find all pending (not approved) leaves
    List<Leave> findByIsPermitFalse();

    // Find all approved leaves
    List<Leave> findByIsPermitTrue();

    // Custom Query: Find leaves in a specific date range
    @Query("SELECT l FROM Leave l WHERE l.leaveFrom >= :startDate AND l.leaveTo <= :endDate")
    List<Leave> findLeavesBetweenDates(@Param("startDate") String startDate, @Param("endDate") String endDate);

    // Approve leave request by setting isPermit to true
    @Query("UPDATE Leave l SET l.isPermit = true WHERE l.id = :leaveId")
    void approveLeave(@Param("leaveId") Long leaveId);
}
