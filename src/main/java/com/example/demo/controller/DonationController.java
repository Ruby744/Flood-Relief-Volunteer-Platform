package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Donation;
import com.example.demo.repository.DonationRepository;
import com.example.demo.service.DonationService;

@Controller
@RequestMapping("/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;
    
    @Autowired DonationRepository donationRepository;

    @GetMapping("/form")
    public String showDonationForm(Model model) {
        model.addAttribute("donation", new Donation());
        return "donationForm"; 
    }

    @PostMapping("/submit")
    public String saveDonation(
            @ModelAttribute Donation donation,
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
            @RequestParam String type,
            @RequestParam(required = false) Double amount,
            @RequestParam( required = false) Integer quantity,
            @RequestParam(required = false) String description,
            Model model) {
        
        try {
        	
        	if ("MONEY".equals(type)) {
                donation.setAmount(amount); // Only set the amount for money donations
            } 
        	else if ("ITEMS".equals(type)) {
                donation.setQuantity(quantity);
                donation.setDescription(description); 
            }
        	
        	
            // Save the donation
            Donation savedDonation = donationService.saveDonation(donation, title, firstName, lastName, email, phone,
            		country, city, building, street, postal, placeId);

            // Add success message to the model
            model.addAttribute("message", "Donation saved successfully!");
            model.addAttribute("donation", savedDonation);
            return "donationSuccess"; // Name of the Thymeleaf template for success
        } catch (IllegalArgumentException e) {
            // Add error message to the model
            model.addAttribute("error", e.getMessage());
            model.addAttribute("donation", donation);
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
            return "donationForm"; // Return back to the form with an error
        }
    }
    
    //Fetch donation history dynamically based on place ID
    @GetMapping("/history")
    public String getDonationHistory(@RequestParam Long placeId, Model model) {
        List<Donation> donations = donationRepository.findByPlaceId(placeId);
        model.addAttribute("donations", donations);
        return "fragments/donationHistory :: historyTable";
    }
}
