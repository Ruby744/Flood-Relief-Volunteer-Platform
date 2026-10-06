package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.service.VolunteerService;
import com.example.demo.repository.VolunteerRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.entity.Volunteer;
import com.example.demo.entity.User;

@Service
public class VolunteerServiceImpl implements VolunteerService {
    @Autowired
    private VolunteerRepository volunteerRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    public VolunteerServiceImpl(VolunteerRepository volunteerRepository,
                               UserRepository userRepository,
                               PlaceRepository placeRepository) {
        this.volunteerRepository = volunteerRepository;
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
    }

    @Override
    public Volunteer saveVolunteer(Volunteer volunteer, String title, String firstName, String lastName, String email, String phone, String country, String city, String building, String street, String postal, Long placeId) {
        // Check if the user exists by email or phone
        User existingUser = userRepository.findByEmailOrPhone(email, phone);

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
             userRepository.save(existingUser);
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
            userRepository.save(newUser);
            volunteer.setUser(newUser);
        }

        // Map the place to the donation
        volunteer.setPlace(placeRepository.findById(placeId).orElseThrow(() -> new IllegalArgumentException("Invalid place ID")));
        
        // Save the donation
        return volunteerRepository.save(volunteer);
    }
	
}