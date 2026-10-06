package com.example.demo.service;

import com.example.demo.entity.Volunteer;


public interface VolunteerService {
    
    Volunteer saveVolunteer(Volunteer volunteer, String title, String firstName, String lastName,
    		String email, String phone, String country, String city, String building, String street, 
    		String postal, Long placeId);
}