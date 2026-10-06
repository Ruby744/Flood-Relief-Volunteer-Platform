package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Volunteer;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
	
    List<Volunteer> findByUserId(Long userId);
    List<Volunteer> findByPlaceId(Long placeId);
    

}