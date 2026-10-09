package com.alpha.customer.entity;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
@Entity
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String paymenttype;
	private String bookingdate;
	private String bookingtime;
	private String pickuptime;
	private String droptime;
	private double fare;
	private int riderid;
	private String bstatus;
	public String paymentstatus;
	private String pickup;
	private String drop;
	private String duration;
	private double distance;
	private double ridershare;
	private double platformshare;
	@Column(name = "idempotent_id", unique = true, nullable = false)
	private String idempotentId;

	@ManyToOne(cascade = CascadeType.ALL)
	private Customer customers;
	@OneToOne(cascade = CascadeType.ALL)
	private Coordinates source;
	@OneToOne(cascade = CascadeType.ALL)
	private Coordinates destination;
	private String vehciletype;
	public Booking(String paymenttype, String bookingdate, String bookingtime, String pickuptime, String droptime,
			double fare, int riderid, String bstatus, String pickup, String drop, String duration, double distance,
			double ridershare, double platformshare, String idempotentId, Customer customers, Coordinates source,
			Coordinates destination, String vehciletype,String paymentstatus) {
		super();
		this.paymenttype = paymenttype;
		this.bookingdate = bookingdate;
		this.bookingtime = bookingtime;
		this.pickuptime = pickuptime;
		this.droptime = droptime;
		this.fare = fare;
		this.riderid = riderid;
		this.bstatus = bstatus;
		this.pickup = pickup;
		this.drop = drop;
		this.duration = duration;
		this.distance = distance;
		this.ridershare = ridershare;
		this.platformshare = platformshare;
		this.idempotentId = idempotentId;
		this.customers = customers;
		this.source = source;
		this.destination = destination;
		this.vehciletype = vehciletype;
		this.paymentstatus=paymentstatus;
	}
	public Booking() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public String getBookingdate() {
		return bookingdate;
	}
	public void setBookingdate(String bookingdate) {
		this.bookingdate = bookingdate;
	}
	public String getBookingtime() {
		return bookingtime;
	}
	public void setBookingtime(String bookingtime) {
		this.bookingtime = bookingtime;
	}
	public String getPickuptime() {
		return pickuptime;
	}
	public void setPickuptime(String pickuptime) {
		this.pickuptime = pickuptime;
	}
	public String getDroptime() {
		return droptime;
	}
	public void setDroptime(String droptime) {
		this.droptime = droptime;
	}
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
	}
	public int getRiderid() {
		return riderid;
	}
	public void setRiderid(int riderid) {
		this.riderid = riderid;
	}
	public String getBstatus() {
		return bstatus;
	}
	public void setBstatus(String bstatus) {
		this.bstatus = bstatus;
	}
	public String getPickup() {
		return pickup;
	}
	public void setPickup(String pickup) {
		this.pickup = pickup;
	}
	public String getDrop() {
		return drop;
	}
	public void setDrop(String drop) {
		this.drop = drop;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getRidershare() {
		return ridershare;
	}
	public void setRidershare(double ridershare) {
		this.ridershare = ridershare;
	}
	public double getPlatformshare() {
		return platformshare;
	}
	public void setPlatformshare(double platformshare) {
		this.platformshare = platformshare;
	}
	public String getIdempotentId() {
		return idempotentId;
	}
	public void setIdempotentId(String idempotentId) {
		this.idempotentId = idempotentId;
	}
	public Customer getCustomers() {
		return customers;
	}
	public void setCustomers(Customer customers) {
		this.customers = customers;
	}
	public Coordinates getSource() {
		return source;
	}
	public void setSource(Coordinates source) {
		this.source = source;
	}
	public Coordinates getDestination() {
		return destination;
	}
	public void setDestination(Coordinates destination) {
		this.destination = destination;
	}
	public String getVehciletype() {
		return vehciletype;
	}
	public void setVehciletype(String vehciletype) {
		this.vehciletype = vehciletype;
	}
	public String getPaymentstatus() {
		return paymentstatus;
	}
	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}
	
	
	
	
}