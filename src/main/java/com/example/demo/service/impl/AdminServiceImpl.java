package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Donation;
import com.example.demo.entity.Place;
import com.example.demo.entity.User;
import com.example.demo.entity.Volunteer;
import com.example.demo.repository.DonationRepository;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VolunteerRepository;
import com.example.demo.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private VolunteerRepository volunteerRepo;
	
	@Autowired
	private DonationRepository donationRepo;
	
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private PlaceRepository projectRepo;
	
	public AdminServiceImpl(DonationRepository donationRepo, UserRepository userRepo, PlaceRepository projectRepo, VolunteerRepository volunteerRepo) {
		super();
		this.donationRepo = donationRepo;
		this.userRepo = userRepo;
		this.projectRepo = projectRepo;
		this.volunteerRepo = volunteerRepo;
	}
	
	//volunteers
	@Override
	public List<Volunteer> getAllVolunteers(){
		return volunteerRepo.findAll();
	}

	 @Override
    public Volunteer saveVolunteer(Volunteer volunteer, String title, String firstName, String lastName, String email, String phone, String country, String city, String building, String street, String postal, Long placeId) {
        // Check if the user exists by email or phone
        User existingUser = userRepo.findByEmailOrPhone(email, phone);

        if(existingUser != null){
            
            // Validate phone number matches the email
        	 if(!existingUser.getPhone().equals(phone) || !existingUser.getEmail().equals(email))  {
                throw new IllegalArgumentException("The provided email or phone does not match the existing records. Please re-enter correct info.");
            }
            
        	// Update user details if they are null or not the same
        	 if(existingUser.getTitle() == null || !existingUser.getTitle().equals(title)){
        	     existingUser.setTitle(title);
        	 }
        	 if(existingUser.getFirstName() == null || !existingUser.getFirstName().equals(firstName)){
        	     existingUser.setFirstName(firstName);
        	 }
        	 if(existingUser.getLastName() == null || !existingUser.getLastName().equals(lastName)){
        	     existingUser.setLastName(lastName);
        	 }
        	 if(existingUser.getCountry() == null || !existingUser.getCountry().equals(country)){
        	     existingUser.setCountry(country);
        	 }
        	 if(existingUser.getCity() == null || !existingUser.getCity().equals(city)){
        	     existingUser.setCity(city);
        	 }
        	 if(existingUser.getBuilding() == null || !existingUser.getBuilding().equals(building)){
        	     existingUser.setBuilding(building);
        	 }
        	 if(existingUser.getStreet() == null || !existingUser.getStreet().equals(street)){
        	     existingUser.setStreet(street);
        	 }
        	 if(existingUser.getPostal() == null || !existingUser.getPostal().equals(postal)){
        	     existingUser.setPostal(postal);
        	 }


             // Save the updated user
             userRepo.save(existingUser);
            // Map the existing user to the donation
             volunteer.setUser(existingUser);
        } 
        else{
            // Add a new user if email and phone do not exist
            User newUser = new User();
            newUser.setTitle(title);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setEmail(email);
            newUser.setPhone(phone);
            newUser.setCountry(country);
            newUser.setCity(city);
            newUser.setBuilding(building);
            newUser.setStreet(street);
            newUser.setPostal(postal);
            userRepo.save(newUser);
            volunteer.setUser(newUser);
        }

        // Map the place to the donation
        volunteer.setPlace(projectRepo.findById(placeId).orElseThrow(() -> new IllegalArgumentException("Invalid place ID")));
        
        // Save the donation
        return volunteerRepo.save(volunteer);
    }

	@Override
	public Volunteer getVolunteerById(Long id) {
		return volunteerRepo.findById(id).get();
	}

	@Override
	public Volunteer updateVolunteer(Volunteer volunteer) {

        return volunteerRepo.save(volunteer);
	}

	@Override
	public void deleteVolunteerById(Long id) {
		volunteerRepo.deleteById(id);
	}
	
	
	
	//donations
	@Override
	public List<Donation> getAllDonations(){
		return donationRepo.findAll();
	}

	 @Override
    public Donation saveDonation(Donation donation, String title, String firstName, String lastName, String email, String phone, String country, String city, String building, String street, String postal, Long placeId) {
        // Check if the user exists by email or phone
        User existingUser = userRepo.findByEmailOrPhone(email, phone);

        if(existingUser != null){
            
            // Validate phone number matches the email
        	 if(!existingUser.getPhone().equals(phone) || !existingUser.getEmail().equals(email))  {
                throw new IllegalArgumentException("The provided email or phone does not match the existing records. Please re-enter correct info.");
            }
            
        	// Update user details if they are null or not the same
        	 if(existingUser.getTitle() == null || !existingUser.getTitle().equals(title)){
        	     existingUser.setTitle(title);
        	 }
        	 if(existingUser.getFirstName() == null || !existingUser.getFirstName().equals(firstName)){
        	     existingUser.setFirstName(firstName);
        	 }
        	 if(existingUser.getLastName() == null || !existingUser.getLastName().equals(lastName)){
        	     existingUser.setLastName(lastName);
        	 }
        	 if(existingUser.getCountry() == null || !existingUser.getCountry().equals(country)){
        	     existingUser.setCountry(country);
        	 }
        	 if(existingUser.getCity() == null || !existingUser.getCity().equals(city)){
        	     existingUser.setCity(city);
        	 }
        	 if(existingUser.getBuilding() == null || !existingUser.getBuilding().equals(building)){
        	     existingUser.setBuilding(building);
        	 }
        	 if(existingUser.getStreet() == null || !existingUser.getStreet().equals(street)){
        	     existingUser.setStreet(street);
        	 }
        	 if(existingUser.getPostal() == null || !existingUser.getPostal().equals(postal)){
        	     existingUser.setPostal(postal);
        	 }


             // Save the updated user
             userRepo.save(existingUser);
            // Map the existing user to the donation
            donation.setUser(existingUser);
        } 
        else{
            // Add a new user if email and phone do not exist
            User newUser = new User();
            newUser.setTitle(title);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setEmail(email);
            newUser.setPhone(phone);
            newUser.setCountry(country);
            newUser.setCity(city);
            newUser.setBuilding(building);
            newUser.setStreet(street);
            newUser.setPostal(postal);
            userRepo.save(newUser);
            donation.setUser(newUser);
        }

        // Map the place to the donation
        donation.setPlace(projectRepo.findById(placeId).orElseThrow(() -> new IllegalArgumentException("Invalid place ID")));
        
        // Save the donation
        return donationRepo.save(donation);
    }

	@Override
	public Donation getDonationById(Long id) {
		return donationRepo.findById(id).get();
	}

	@Override
	public Donation updateDonation(Donation donation) {

        return donationRepo.save(donation);
	}

	@Override
	public void deleteDonationById(Long id) {
		donationRepo.deleteById(id);
	}
	
	
	//user
	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User saveUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public User getUserById(Long id) {
		return userRepo.findById(id).get();
	}

	@Override
	public User updateUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public void deleteUserById(Long id) {
	    try {
	        userRepo.deleteById(id);
	    } catch (DataIntegrityViolationException e) {
	        throw new RuntimeException("Cannot delete user due to associated dependencies.");
	    }
	}


	
	//project
	@Override
	public List<Place> getAllProjects() {
		return projectRepo.findAll();
	}

	@Override
	public Place saveProject(Place project) {
		return projectRepo.save(project);
	}

	@Override
	public Place getProjectById(Long id) {
		return projectRepo.findById(id).get();
	}

	@Override
	public Place updateProject(Place project) {
		return projectRepo.save(project);
	}

	@Override
	public void deleteProjectById(Long id) {
	    try {
	        projectRepo.deleteById(id);
	    } catch (DataIntegrityViolationException e) {
	        throw new RuntimeException("Cannot delete project due to associated dependencies.");
	    }
	}

}
