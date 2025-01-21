package com.eagle.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eagle.service.impl.AIService;


@Controller
public class AIController {

    @Autowired
    private AIService aiService;

    @GetMapping("/ai")
    public String showForm() {
        return "ai";
    }

    @PostMapping("/get-response")
    public String getAIResponse(@RequestParam String prompt, Model model) {
        String aiResponse = aiService.getAIResponse(prompt);

        // Parse the AI response (JSON) to extract useful data
        String formattedResponse = aiService.formatAIResponse(aiResponse);

        model.addAttribute("response", formattedResponse);
        return "ai";
    }
}
