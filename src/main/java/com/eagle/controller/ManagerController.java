package com.eagle.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eagle.entities.Project;
import com.eagle.entities.User;
import com.eagle.entities.UserLogs;
import com.eagle.repository.UserLogsRepository;
import com.eagle.repository.UserRepository;
import com.eagle.service.ProjectService;
import com.eagle.service.UserLogsService;
import com.eagle.service.UserService;

@Controller
@RequestMapping("/manager")
public class ManagerController {
	
	@Autowired
	UserLogsService userLogsService;
	
	@Autowired
	UserLogsRepository userLogsRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/assign-project")
    public String showAssignProjectForm(Model model) {
        return "managers/assign-project";
    }

    @PostMapping("/assign-project")
    public String assigningProject(
            @RequestParam("projectName") String projectName,
            @RequestParam("employeeId") Long employeeId,
            RedirectAttributes redirectAttributes) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User manager = userRepository.findByEmail(username);

        User emp = userService.getUserById(employeeId);
        if (emp == null) {
            redirectAttributes.addFlashAttribute("error", "Employee not found with ID: " + employeeId);
            return "redirect:/manager/assign-project";
        }
        Project p = new Project();
        p.setProjectName(projectName);
        p.setAssignBy(manager.getName());
        p.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        p.setEndDate(null);
        p.setRemark(null);
        p.setUser(emp);
        emp.getProjects().add(p);
        projectService.addProject(p);
        userService.updateUser(emp);
        redirectAttributes.addFlashAttribute("success", "Project assigned successfully to "+ emp.getName());
        return "redirect:/manager/assign-project";
    }
    
    @GetMapping("/submit-project/{projectId}/{remark}")
    public String updateProject(
            @PathVariable("projectId") Long id,
            @PathVariable("remark") Character remark,
            RedirectAttributes redirectAttributes
    ) {
        Project project = projectService.getProjectById(id);
        if (project != null) {
            project.setRemark(remark);
            project.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            projectService.updateProject(project);
            redirectAttributes.addFlashAttribute("success", "Project updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Project not found or already completed!");
        }
        return "redirect:/all-projects";
    }

	    @GetMapping("/my-team")
	    public String getTeam(Model model) {
	    	 String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    	    User loggedUser = userRepository.findByEmail(username);
	    	    List<User> employees = userRepository.findEmployeesByManager(loggedUser);
	    	    model.addAttribute("users", employees);
	        return "team";
	    }
}
