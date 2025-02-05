package com.eagle.controller;

import com.eagle.entities.Leave;
import com.eagle.entities.User;
import com.eagle.repository.UserRepository;
import com.eagle.service.LeaveService;
import com.eagle.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    // Show apply leave form
    @GetMapping("/apply")
    public String showApplyLeaveForm(Model model) {
        model.addAttribute("leave", new Leave());
        return "apply_leave";
    }

    // Submit leave request
    @PostMapping("/apply")
    public String applyLeave(@ModelAttribute Leave leave) {
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User employee = userRepository.findByEmail(username);
    	leaveService.applyLeave(leave, employee.getId());
        return "redirect:/leaves/myLeaves?userId=" + employee.getId();
    }

    // Show logged-in user's leave requests
    @GetMapping("/myLeaves")
    public String showMyLeaves(@RequestParam Long userId, Model model) {
        List<Leave> leaves = leaveService.getLeavesByUserId(userId);
        model.addAttribute("leaves", leaves);
        return "my_leaves";
    }

    // Show all pending leaves for admin/manager
    @GetMapping("/pending")
    public String showPendingLeaves(Model model) {
        List<Leave> pendingLeaves = leaveService.getPendingLeaves();
        model.addAttribute("pendingLeaves", pendingLeaves);
        return "pending_leaves";
    }

    // Approve leave request
    @GetMapping("/approve/{id}")
    public String approveLeave(@PathVariable Long id) {
        leaveService.approveLeave(id);
        return "redirect:/leaves/pending";
    }

    // Delete leave request
    @GetMapping("/delete/{id}")
    public String deleteLeave(@PathVariable Long id) {
        leaveService.deleteLeave(id);
        return "redirect:/leaves/pending";
    }
}
