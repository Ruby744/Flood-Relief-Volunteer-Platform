package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Donation;
import com.example.demo.entity.Place;
import com.example.demo.entity.User;
import com.example.demo.entity.Volunteer;


public interface AdminService {

	List<Volunteer> getAllVolunteers();
	Volunteer saveVolunteer(Volunteer donation, String title, String firstName, String lastName,
    		String email, String phone, String country, String city, String building, String street, 
    		String postal, Long placeId);
	Volunteer getVolunteerById(Long id);
	Volunteer updateVolunteer(Volunteer volunteer);
	void deleteVolunteerById(Long id);
	
	List<Donation> getAllDonations();
    Donation saveDonation(Donation donation, String title, String firstName, String lastName,
    		String email, String phone, String country, String city, String building, String street, 
    		String postal, Long placeId);
	Donation getDonationById(Long id);
	Donation updateDonation(Donation donation);
	void deleteDonationById(Long id);
	
	List<User> getAllUsers();
	User saveUser(User user);
	User getUserById(Long id);
	User updateUser(User user);
	void deleteUserById(Long id);
	
	List<Place> getAllProjects();
	Place saveProject(Place project);
	Place getProjectById(Long id);
	Place updateProject(Place project);
	void deleteProjectById(Long id);
}
