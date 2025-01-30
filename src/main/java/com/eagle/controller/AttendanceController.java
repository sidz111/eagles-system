package com.eagle.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eagle.entities.Attendance;
import com.eagle.entities.User;
import com.eagle.repository.UserRepository;
import com.eagle.service.AttendanceService;

@Controller
public class AttendanceController {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	AttendanceService attendanceService;

//	@GetMapping("/list")
//	public String viewAllAttendance(Model model) {
//		List<Attendance> attendanceList = attendanceService.getAllAttendance();
//		model.addAttribute("attendanceList", attendanceList);
//		return "attendance_list";
//	}
	@GetMapping("/list")
	public String viewAllAttendance(Model model) {
	    List<Attendance> attendanceList = attendanceService.getAllAttendance();
	    model.addAttribute("attendanceList", attendanceList);
	    return "attendance_list";
	}

	@GetMapping("/attendance")
	public String showAttendancePage(Model model) {
		return "attendance"; // Render Thymeleaf page
	}
	
	  @PostMapping("/submitAttendance")
	    public String submitAttendance(
	            @RequestParam String latitude,
	            @RequestParam String longitude,
	            @RequestParam String currentLocation,
	            Model model) {

	        // Get authenticated user
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String username = authentication.getName();
	        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(username));

	        if (userOptional.isPresent()) {
	            User user = userOptional.get();

	            // Create attendance entry
	            Attendance attendance = new Attendance();
	            attendance.setDateTime(LocalDateTime.now());
	            attendance.setLatitude(latitude);
	            attendance.setLongitude(longitude);
	            attendance.setCurrentLocation(currentLocation);
	            attendance.setIsPresent(true);
	            attendance.setUser(user);

	            // Save to DB
	            attendanceService.saveAttendance(attendance);

	            model.addAttribute("message", "Attendance recorded successfully!");
	        } else {
	            model.addAttribute("error", "User not found!");
	        }

	        return "attendance"; // Reload page with message
	    }

	@GetMapping("/user/details")
	@ResponseBody
	public Map<String, Object> getLoggedInUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> userDetails = new HashMap<>();

		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails user = (UserDetails) authentication.getPrincipal();
			userDetails.put("username", user.getUsername());
			userDetails.put("roles", user.getAuthorities().toString());
		} else {
			userDetails.put("username", "Guest");
			userDetails.put("roles", "N/A");
		}

		return userDetails;
	}
}
