package com.alpha.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.customer.dto.AcceptBookingResponseDto;
import com.alpha.customer.dto.CompleteRideResponseDto;
import com.alpha.customer.dto.Coordinatesdto;
import com.alpha.customer.dto.Customerdto;
import com.alpha.customer.dto.EarningSummaryDto;
import com.alpha.customer.dto.Fareresponsedto;
import com.alpha.customer.dto.ResponseStructure;
import com.alpha.customer.dto.RiderHistoryDto;
import com.alpha.customer.dto.SearchDestlocResponsedto;
import com.alpha.customer.dto.Selectridedto;
import com.alpha.customer.dto.VerifyOtpResponseDto;
import com.alpha.customer.entity.Booking;
import com.alpha.customer.entity.Coordinates;
import com.alpha.customer.entity.Customer;
import com.alpha.customer.service.Coordinateservice;
import com.alpha.customer.service.CustomerService;
import com.alpha.customer.service.RedisService;

@RestController
public class Customercontroller {
	@Autowired
	private CustomerService customservice;
	@Autowired
	private Coordinateservice coser;
	@Autowired
	private RedisService rediservice;
	
	@PostMapping("/customer/create")
	public void createcutommerac(@RequestBody Customerdto cdto) {
		customservice.customeraccreate(cdto);
	}
	@GetMapping("/customer/find")
	public ResponseStructure<Customer> customerfind(@RequestParam int id) {
		
		return customservice.findcustomer(id);
	}
	@DeleteMapping("/customer/delete")
	public ResponseStructure<Customer> deletecustomer(@RequestParam int id) {
		return customservice.customerdelete(id);
	}
	
	@GetMapping("/customer/searchdroploc")
	public List<SearchDestlocResponsedto> searchdroplocation(@RequestParam String searchkey) {
		return customservice.searchdroplocation(searchkey);
	}
	@PostMapping("/customer/ridecalculation")
	public Fareresponsedto ridefarecal(@RequestBody Selectridedto sdto,@RequestParam int id) {
		return coser.selectriderservice(sdto,id);
	}
	
	public void customerrideselect(Selectridedto sdto) {
		customservice.Selectride(sdto);
	}
	@PostMapping("/customer/confirmride")
	public Booking confirmride(@RequestParam int id,@RequestParam String vehtype,@RequestBody Coordinatesdto cdto) {
		return customservice.confirmridebooking(id,vehtype,cdto);
	}
	
	@PostMapping("/booking/accept")
	public String acceptedbooking(@RequestParam int rid,@RequestParam int bid) {
		return customservice.acceptedbooking(rid,bid);
	}
	 
	//############################################################################################################################################
	
	@PostMapping("/customer/assignrider")
	public AcceptBookingResponseDto assignRider(@RequestParam int riderid, @RequestParam int bookingid) {

		return customservice.assignRiderService(riderid, bookingid);
	}

	@GetMapping("/customer/moveTowardsPickup")
	public String moveTowardsPickup(@RequestParam int bookingid) {

		return customservice.moveTowardsPickupService(bookingid);
	}

	@PostMapping("/customer/UpdateToArrived")
	public String updateToArrived(@RequestParam int bookingid) {
		return customservice.updateToArrivedService(bookingid);
	}

	@PostMapping("/customer/verifyotp")
	public VerifyOtpResponseDto verifyOtp(@RequestParam int bookingid, @RequestParam String otp) {

		return customservice.verifyOtpService(bookingid, otp);
	}

	@GetMapping("/customer/moveTowardsDrop")
	public String moveTowardsDrop(@RequestParam int bookingid) {

		return customservice.moveTowardsDropService(bookingid);
	}

	@PostMapping("/customer/UpdateToReached")
	public String updateToReached(@RequestParam int bookingid) {
		return customservice.updateToReachedService(bookingid);
	}

	@PostMapping("/customer/completeRide")
	public CompleteRideResponseDto completeRide(@RequestParam int bookingid) {

		return customservice.completeRideService(bookingid);
	}
	//******************************************************************************

	@GetMapping("/customer/ridehistory")
	public List<Booking> rideHistory(@RequestParam int customerid, @RequestParam String status) {

		return customservice.rideHistoryService(customerid, status);
	}

	@GetMapping("/customer/riderhistory")
	public List<RiderHistoryDto> riderHistory(@RequestParam int riderid, @RequestParam String status) {

		return customservice.riderHistoryService(riderid, status);
	}

	@GetMapping("/customer/earning/summary")
	public EarningSummaryDto earningSummary(@RequestParam int riderid, @RequestParam String from,
			@RequestParam String to) {

		return customservice.earningSummaryService(riderid, from, to);
	}
	
	
}
