package com.alpha.customer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.customer.entity.Booking;

public interface Bookingrepository extends JpaRepository<Booking, Integer>{

	Optional<Booking> findByIdempotentId(String idempotentId);

	List<Booking> findByCustomerId(int custid);

	List<Booking> findByCustomerIdAndStatus(int custid, String bstatus);

	List<Booking> findByRiderid(int riderid);

	List<Booking> findByRideridAndStatus(int riderid, String bstatus);

	List<Booking> findByRideridAndStatusAndBookingdateBetween(int riderid, String bstatus, String from, String to);
}


