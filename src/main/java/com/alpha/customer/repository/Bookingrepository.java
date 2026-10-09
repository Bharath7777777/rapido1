package com.alpha.customer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.customer.entity.Booking;

public interface Bookingrepository
extends JpaRepository<Booking, Integer> {

Optional<Booking> findByIdempotentId(String idempotentId);

List<Booking> findByCustomers_Custid(int custid);

List<Booking> findByCustomers_CustidAndBstatus(int custid, String bstatus);

List<Booking> findByRiderid(int riderid);

List<Booking> findByRideridAndBstatus( int riderid, String bstatus);

List<Booking> findByRideridAndBstatusAndBookingdateBetween(int riderid, String bstatus, String from, String to);
}
