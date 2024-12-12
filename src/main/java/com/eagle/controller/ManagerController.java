package com.eagle.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eagle.entities.Project;
import com.eagle.entities.User;
import com.eagle.repository.UserRepository;
import com.eagle.service.ProjectService;
import com.eagle.service.UserService;

@Controller
@RequestMapping("/manager")
public class ManagerController {

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
        p.setStartDate(new Date().toString());
        p.setEndDate(null);
        p.setRemark(null);
        p.setUser(emp);
        emp.getProjects().add(p);
        projectService.addProject(p);
        userService.updateUser(emp);
        redirectAttributes.addFlashAttribute("success", "Project assigned successfully.");
        return "redirect:/manager/assign-project";
    }
}
