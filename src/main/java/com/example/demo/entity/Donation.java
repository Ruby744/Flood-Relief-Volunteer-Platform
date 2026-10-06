package com.example.demo.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "donations")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private Double amount;
    private String description;
    private Integer quantity;
    private boolean receiveUpdate;
    
    @CreationTimestamp
    @Column(name = "donation_date", nullable = false, updatable = false)
    private LocalDateTime donationDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Place place;

    
    public Donation() {}
    
	public Donation(String type, Double amount, String description, Integer quantity, boolean receiveUpdate, User user, Place place) {
		super();
		this.type = type;
		this.amount = amount;
		this.description = description;
		this.quantity = quantity;
		this.receiveUpdate = receiveUpdate;
		this.user = user;
		this.place = place;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public boolean isReceiveUpdate() {
		return receiveUpdate;
	}

	public void setReceiveUpdate(boolean receiveUpdate) {
		this.receiveUpdate = receiveUpdate;
	}
	
	public LocalDateTime getDonationDate() {
		return donationDate;
	}

	public void setDonationDate(LocalDateTime donationDate) {
		this.donationDate = donationDate;
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