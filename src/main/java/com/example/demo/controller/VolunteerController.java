package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Place;
import com.example.demo.entity.Volunteer;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.service.AdminService;
import com.example.demo.service.VolunteerService;

@Controller
public class VolunteerController {
	
	@Autowired
    private final VolunteerService volunteerService;
	
	private AdminService adminService;
	
	@Autowired
	private PlaceRepository placeRepository;
    
    public VolunteerController(VolunteerService volunteerService, AdminService adminService) {
        this.volunteerService = volunteerService;
        this.adminService = adminService;
    }
    
    @GetMapping("/volunteer")
    public String showEvents(Model model) {
        List<Place> projects = adminService.getAllProjects();
        model.addAttribute("projects", projects);
        return "volunteer";
    }
    
    @PostMapping("/api/events/{eventId}/register")
    public String saveVolunteer(
            @PathVariable Long eventId,
            @ModelAttribute Volunteer volunteer,
            @RequestParam String title,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String country,
            @RequestParam String city,
            @RequestParam String building,
            @RequestParam String street,
            @RequestParam String postal,
            @RequestParam Long placeId,
            @RequestParam String skills,
            @RequestParam String state,
            Model model,
            RedirectAttributes redirectAttributes) {

        try {
            // Retrieve the Place by its ID (which includes the event's details, such as capacity)
            Optional<Place> optionalPlace = placeRepository.findById(placeId);
            if (optionalPlace.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Place not found.");
                return "redirect:/volunteer";
            }

            Place place = optionalPlace.get();

            // Check if the Place (event's capacity) has available capacity
            if (place.getCapacity() <= 0) {
                redirectAttributes.addFlashAttribute("errorMessage", "No capacity available for this event.");
                return "redirect:/volunteer";
            }

            // Save the volunteer details
            Volunteer savedVolunteer = volunteerService.saveVolunteer(volunteer, title, firstName, lastName, email, phone,
                    country, city, building, street, postal, placeId);

            // Deduct the capacity directly from the Place entity
            place.setCapacity(place.getCapacity() - 1);
            placeRepository.save(place);  // Save the updated Place to deduct capacity

            // Add success message
            redirectAttributes.addFlashAttribute("successMessage", "Volunteer registered successfully!");
            return "redirect:/volunteer";
        } catch (IllegalArgumentException e) {
            // Handle any validation errors or exceptions
            model.addAttribute("error", e.getMessage());
            model.addAttribute("volunteer", volunteer);
            model.addAttribute("title", title);
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            model.addAttribute("email", email);
            model.addAttribute("phone", phone);
            model.addAttribute("country", country);
            model.addAttribute("city", city);
            model.addAttribute("building", building);
            model.addAttribute("street", street);
            model.addAttribute("postal", postal);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/volunteer";
        }
    }

}