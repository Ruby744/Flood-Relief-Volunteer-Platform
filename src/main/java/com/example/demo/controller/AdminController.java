package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Donation;
import com.example.demo.entity.Place;
import com.example.demo.entity.User;
import com.example.demo.entity.Volunteer;
import com.example.demo.service.AdminService;
import com.example.demo.service.DonationService;
import com.example.demo.service.VolunteerService;

@Controller
public class AdminController {
	
	private AdminService adminService;
	
    @Autowired
    private DonationService donationService;
    
    @Autowired
    private VolunteerService volunteerService;
	
	public AdminController(AdminService adminService) {
		super();
		this.adminService = adminService;
	}
	
	//login and dashboard
    @GetMapping("/admin-login")
    public String loginPage() {
        return "admin-login";
    }
    
    @GetMapping("/admin")
    public String adminDashboard() {
        return "admin";
    }

    
    
    //volunteer
    @GetMapping("/admin-volunteer")
    public String listVolunteers(Model model) {
    	model.addAttribute("volunteers", adminService.getAllVolunteers());
    	return "admin-volunteer";
    }
    
    @GetMapping("/admin-volunteer/new")
    public String createVolunteerForm(Model model) {
    	Volunteer volunteer = new Volunteer();
		model.addAttribute("volunteer", volunteer);
    	return "create_volunteer";
    }
    
    @PostMapping("/admin-volunteer")
    public String saveVolunteer(
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
            @RequestParam String state,
            @RequestParam String skills,
            Model model) {
        
        try {
            // Save the volunteer
            Volunteer savedVolunteer = volunteerService.saveVolunteer(volunteer, title, firstName, lastName, email, phone,
            		country, city, building, street, postal, placeId);

            // Add success message to the model
            model.addAttribute("message", "Volunteer saved successfully!");
            model.addAttribute("volunteer", savedVolunteer);
            return "redirect:/admin-volunteer";
        } catch (IllegalArgumentException e) {
            // Add error message to the model
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
            return "create_volunteer";
        }
    }
    
    @GetMapping("/admin-volunteer/edit/{id}")
    public String editVolunteerForm(@PathVariable Long id, Model model) {
    	Volunteer volunteer = adminService.getVolunteerById(id);
    	model.addAttribute("volunteer", volunteer);
    	model.addAttribute("user", volunteer.getUser());
    	model.addAttribute("title", volunteer.getUser().getTitle());
    	model.addAttribute("country", volunteer.getUser().getCountry());
    	model.addAttribute("place", volunteer.getPlace());
    	model.addAttribute("skills", volunteer.getSkills());
    	model.addAttribute("state", volunteer.getState());
    	return "edit_volunteer";
    }
    
    @PostMapping("/admin-volunteer/{id}")
    public String updateVolunteer(@PathVariable Long id, 
    		@ModelAttribute("volunteer") Volunteer volunteer,
    		Model model) {
    	Volunteer existingVolunteer = adminService.getVolunteerById(id);
    	existingVolunteer.setId(id);
    	existingVolunteer.setSkills(volunteer.getSkills());
    	existingVolunteer.setState(volunteer.getState());
    	existingVolunteer.setPlace(volunteer.getPlace());
    	adminService.updateVolunteer(existingVolunteer);
    	return "redirect:/admin-volunteer";
    }
    
    @GetMapping("/admin-volunteer/{id}")
    public String deleteVolunteer(@PathVariable Long id) {
    	adminService.deleteVolunteerById(id);
    	return "redirect:/admin-volunteer";
    }
    
    
    
    //donation
    @GetMapping("/admin-donation")
    public String listDonations(Model model) {
    	model.addAttribute("donations", adminService.getAllDonations());
    	return "admin-donation";
    }
    
    @GetMapping("/admin-donation/new")
    public String createDonationForm(Model model) {
    	Donation donation = new Donation();
		model.addAttribute("donation", donation);
    	return "create_donation";
    }
    
    @PostMapping("/admin-donation")
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
            return "redirect:/admin-donation";
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
            return "create_donation";
        }
    }
    
    @GetMapping("/admin-donation/edit/{id}")
    public String editDonationForm(@PathVariable Long id, Model model) {
    	Donation donation = adminService.getDonationById(id);
    	model.addAttribute("donation", donation);
    	model.addAttribute("type", donation.getType());
    	model.addAttribute("user", donation.getUser());
    	model.addAttribute("title", donation.getUser().getTitle());
    	model.addAttribute("country", donation.getUser().getCountry());
    	model.addAttribute("place", donation.getPlace());
    	return "edit_donation";
    }
    
    @PostMapping("/admin-donation/{id}")
    public String updateDonation(@PathVariable Long id, 
    		@ModelAttribute("donation") Donation donation,
    		Model model) {
    	Donation existingDonation = adminService.getDonationById(id);
    	existingDonation.setId(id);
    	existingDonation.setType(donation.getType());
    	existingDonation.setAmount(donation.getAmount());
    	existingDonation.setDescription(donation.getDescription());
    	existingDonation.setQuantity(donation.getQuantity());
    	existingDonation.setReceiveUpdate(donation.isReceiveUpdate());
    	existingDonation.setDonationDate(donation.getDonationDate());
    	existingDonation.setPlace(donation.getPlace());

    	adminService.updateDonation(existingDonation);
    	return "redirect:/admin-donation";
    }
    
    @GetMapping("/admin-donation/{id}")
    public String deleteDonation(@PathVariable Long id) {
    	adminService.deleteDonationById(id);
    	return "redirect:/admin-donation";
    }
    
    
    //user
    @GetMapping("/admin-user")
    public String listUsers(Model model) {
    	model.addAttribute("users", adminService.getAllUsers());
    	return "admin-user";
    }
    
    @GetMapping("/admin-user/new")
    public String createUserForm(Model model) {
    	User user = new User();
		model.addAttribute("user", user);
    	return "create_user";
    }
    
    @PostMapping("/admin-user")
    public String saveUser(@ModelAttribute("user") User user) {
    	adminService.saveUser(user);
    	return "redirect:/admin-user";
    }
    
    @GetMapping("/admin-user/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
    	model.addAttribute("user", adminService.getUserById(id));
    	return "edit_user";
    }
    
    @PostMapping("/admin-user/{id}")
    public String updateUser(@PathVariable Long id, 
    		@ModelAttribute("user") User user,
    		Model model) {
    	User existingUser = adminService.getUserById(id);
    	existingUser.setId(id);
    	existingUser.setTitle(user.getTitle());
    	existingUser.setFirstName(user.getFirstName());
    	existingUser.setLastName(user.getLastName());
    	existingUser.setEmail(user.getEmail());
    	existingUser.setPhone(user.getPhone());
    	existingUser.setCountry(user.getCountry());
    	existingUser.setCity(user.getCity());
    	existingUser.setBuilding(user.getBuilding());
    	existingUser.setStreet(user.getStreet());
    	existingUser.setPostal(user.getPostal());
    	
    	adminService.updateUser(existingUser);
    	return "redirect:/admin-user";
    }
    
    @GetMapping("/admin-user/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteUserById(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete user. Existing in Volunteers/Donations.");
        }
        return "redirect:/admin-user";
    }

    
    
    
    //project
    @GetMapping("/admin-project")
    public String listProjects(Model model) {
    	model.addAttribute("projects", adminService.getAllProjects());
    	return "admin-project";
    }
    
    @GetMapping("/admin-project/new")
    public String createProjectForm(Model model) {
    	Place project = new Place();
		model.addAttribute("project", project);
    	return "create_project";
    }
    
    @PostMapping("/admin-project")
    public String saveProject(@ModelAttribute("project") Place project) {
    	adminService.saveProject(project);
    	return "redirect:/admin-project";
    }
    
    @GetMapping("/admin-project/edit/{id}")
    public String editProjectForm(@PathVariable Long id, Model model) {
    	model.addAttribute("project", adminService.getProjectById(id));
    	return "edit_project";
    }
    
    @PostMapping("/admin-project/{id}")
    public String updateProject(@PathVariable Long id, 
    		@ModelAttribute("project") Place project,
    		Model model) {
    	Place existingProject = adminService.getProjectById(id);
    	existingProject.setId(id);
    	existingProject.setName(project.getName());
    	existingProject.setEvent(project.getEvent());
    	existingProject.setDescription(project.getDescription());
    	existingProject.setLocation(project.getLocation());
    	existingProject.setCapacity(project.getCapacity());
    	existingProject.setImageUrl(project.getImageUrl());
    	
    	adminService.updateProject(existingProject);
    	return "redirect:/admin-project";
    }
    
    @GetMapping("/admin-project/{id}")
    public String deleteProject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteProjectById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Project deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete project. Existing in Volunteers/Donations.");
        }
        return "redirect:/admin-project";
    }

}
