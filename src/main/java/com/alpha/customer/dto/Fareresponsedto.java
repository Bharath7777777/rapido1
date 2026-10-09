package com.alpha.customer.dto;

import java.util.HashMap;

import com.alpha.customer.entity.Coordinates;

public class Fareresponsedto {
	private Coordinates source;
	private Coordinates destination;
	private double distance;
	private HashMap<String, Double> fare;
	public Fareresponsedto(Coordinates source, Coordinates destination, double distance, HashMap<String, Double> fare) {
		super();
		this.source = source;
		this.destination = destination;
		this.distance = distance;
		this.fare = fare;
	}
	public Fareresponsedto() {
		super();
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
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public HashMap<String, Double> getFare() {
		return fare;
	}
	public void setFare(HashMap<String, Double> fare) {
		this.fare = fare;
	}
	
	
}
