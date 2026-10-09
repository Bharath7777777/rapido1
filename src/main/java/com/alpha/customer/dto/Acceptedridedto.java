package com.alpha.customer.dto;

public class Acceptedridedto {
	
		private int bookingId;
	    private int customerId;
	    private String customerName;
	    private String pickupLocation;
	    private double fare;
	    private double distance;
		public Acceptedridedto(int bookingId, int customerId, String customerName, String pickupLocation, double fare,
				double distance) {
			super();
			this.bookingId = bookingId;
			this.customerId = customerId;
			this.customerName = customerName;
			this.pickupLocation = pickupLocation;
			this.fare = fare;
			this.distance = distance;
		}
		public Acceptedridedto() {
			super();
		}
		public int getBookingId() {
			return bookingId;
		}
		public void setBookingId(int bookingId) {
			this.bookingId = bookingId;
		}
		public int getCustomerId() {
			return customerId;
		}
		public void setCustomerId(int customerId) {
			this.customerId = customerId;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public String getPickupLocation() {
			return pickupLocation;
		}
		public void setPickupLocation(String pickupLocation) {
			this.pickupLocation = pickupLocation;
		}
		public double getFare() {
			return fare;
		}
		public void setFare(double fare) {
			this.fare = fare;
		}
		public double getDistance() {
			return distance;
		}
		public void setDistance(double distance) {
			this.distance = distance;
		}
	    
	    
}
