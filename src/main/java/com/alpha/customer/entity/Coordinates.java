package com.alpha.customer.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Coordinates {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private double longitude;
	private double latittude;
	@ManyToOne(cascade = CascadeType.ALL)
	private Booking booking;
	public Coordinates(double longitude, double latittude,Booking booking) {
		super();
		this.longitude = longitude;
		this.latittude = latittude;
		this.booking=booking;
	}
	public Booking getBooking() {
		return booking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	public Coordinates() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatittude() {
		return latittude;
	}
	public void setLatittude(double latittude) {
		this.latittude = latittude;
	}
	
	
	
	
}
