package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "volunteers")
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skills;
    private String state;

    @ManyToOne
    private User user;

    @ManyToOne
    private Place place;
    
    // Default constructor
    public Volunteer() {}

    // Constructor with User object
    public Volunteer(User user, String skills, String state, Place place) {
        this.user = user;
        this.skills = skills;
        this.state = state;
        this.place = place;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

}