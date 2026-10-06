package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Donation;

public interface DonationService {
    List<Donation> getDonationHistory(Long userId);
    Donation saveDonation(Donation donation, String title, String firstName, String lastName,
    		String email, String phone, String country, String city, String building, String street, 
    		String postal, Long placeId);
}