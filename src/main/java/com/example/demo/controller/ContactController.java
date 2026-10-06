package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

	
	@GetMapping("/contact")
	public String showcontact()
	{
		return "contact";
	}

    @PostMapping("/submit")
    public String handleFormSubmission(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("message") String message,
            Model model) {

        // Add form data to the model
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("message", message);

        // Simulate success (you can save data to a database here)
        model.addAttribute("success", "Your message has been sent successfully!");

        return "aftersubmit"; // Redirects back to the contact page
    }
}
