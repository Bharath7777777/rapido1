package com.alpha.customer.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alpha.customer.dto.AcceptBookingResponseDto;
import com.alpha.customer.dto.CompleteRideResponseDto;
import com.alpha.customer.dto.Coordinatesdto;
import com.alpha.customer.dto.Customerdto;
import com.alpha.customer.dto.EarningSummaryDto;
import com.alpha.customer.dto.ResponseStructure;
import com.alpha.customer.dto.RiderHistoryDto;
import com.alpha.customer.dto.SearchDestlocResponsedto;
import com.alpha.customer.dto.Selectridedto;
import com.alpha.customer.dto.VerifyOtpResponseDto;
import com.alpha.customer.entity.Booking;
import com.alpha.customer.entity.Coordinates;
import com.alpha.customer.entity.Customer;
import com.alpha.customer.exception.BookingnotfoundException;
import com.alpha.customer.exception.CustomernotFoundException;
import com.alpha.customer.exception.RiderAlreadyAcceptedException;
import com.alpha.customer.repository.Bookingrepository;
import com.alpha.customer.repository.CustomerRepository;


@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customrepo;
	@Autowired
	private RestTemplate resttemplate;
	@Autowired
	private RedisTemplate<String, String> redistemplate;
	@Autowired
	private RedisService rediservice;
	@Autowired
	private Bookingrepository bookingrepo;
	
	public void Selectride(Selectridedto sdto) {
		
		Coordinates source=new Coordinates();
		source.setLongitude(sdto.getSourcecoordinate().getLongitude());
		source.setLatittude(sdto.getSourcecoordinate().getLatittude());
		Coordinates destination=new Coordinates();
		destination.setLongitude(sdto.getDestcoordinate().getLatittude());
		destination.setLatittude(sdto.getDestcoordinate().getLatittude());
		
	}
	
	public void customeraccreate(Customerdto cdto) {
		
		Customer c=new Customer();
		c.setCustomername(cdto.getName());
		c.setCustomermobno(cdto.getMobno());
		c.setCustomermail(cdto.getMail());
		c.setCustomergender(cdto.getGender());
		customrepo.save(c);
		int id=c.getCustid();
		String otp=String.format("%04d",id%10000);
		c.setCustomerotp(otp);
		customrepo.save(c);
	}
	
	public ResponseStructure<Customer> findcustomer(int id) {
		Customer c=customrepo.findById(id).orElseThrow(()->new CustomernotFoundException());
		ResponseStructure<Customer> res=new ResponseStructure();
		res.setMessage("Customer Not found Exceptionf");
		res.setData(c);
		res.setStatuscode(HttpStatus.NOT_FOUND.value());
		return res;
	}
	
	public ResponseStructure<Customer> customerdelete(int id) {
		Customer c=customrepo.findById(id).orElseThrow(()->new CustomernotFoundException());
		customrepo.delete(c);
		ResponseStructure<Customer> res=new ResponseStructure();
		res.setMessage("Customer with id  deleted");
		res.setData(c);
		res.setStatuscode(HttpStatus.FOUND.value());
		return res;
		
	}
	
	public List<SearchDestlocResponsedto> searchdroplocation(String searchkey) {
		String token="pk.206ac086285f4e70425bba276eff0aea";
		String url="https://us1.locationiq.com/v1/search"+token+"&q=" +searchkey+"&format=json";
		SearchDestlocResponsedto[] response=resttemplate.getForObject(url,SearchDestlocResponsedto[].class);
		return Arrays.asList(response);
	}

	public Booking  confirmridebooking (int id, String vehtype, Coordinatesdto cdto) {

	    Customer c = customrepo.findById(id)
	            .orElseThrow(() -> new CustomernotFoundException());

	    Map<Object, Object> data = rediservice.getData(id);

	    if (data == null || data.isEmpty()) {
	        throw new CustomernotFoundException();
	    }

	    Booking booking = new Booking();

	    booking.setCustomers(c);
	    booking.setPaymenttype("pending");

	    LocalDate ld = LocalDate.now();
	    LocalDateTime ldt = LocalDateTime.now();

	    booking.setBookingdate(ld.toString());
	    booking.setBookingtime(ldt.toString());

	    String farestring;

	    switch (vehtype.toLowerCase()) {

	        case "bike":
	            farestring = data.get("bike").toString();
	            break;

	        case "auto":
	            farestring = data.get("auto").toString();
	            break;

	        case "cab":
	            farestring = data.get("cab").toString();
	            break;

	        default:
	            throw new IllegalArgumentException(
	                    "Invalid vehicle type. Use bike, auto or cab");
	    }

	    double fare = Double.parseDouble(farestring);

	    booking.setFare(fare);
	    booking.setVehciletype(vehtype);
	    booking.setBstatus("pending");
	    booking.setRiderid(0);
	    booking.setPickuptime("null");
	    booking.setDroptime("null");


	    // Find nearby riders
	    List<String> nearbyriders =
	            rediservice.findNearbyRiders(
	                    vehtype,
	                    cdto.getLongitude(),
	                    cdto.getLatitude()
	            );

	    System.out.println("Vehicle type = " + vehtype);
	    System.out.println("Longitude = " + cdto.getLongitude());
	    System.out.println("Latitude = " + cdto.getLatitude());
	    System.out.println("Nearby riders = " + nearbyriders);

	    if (nearbyriders == null || nearbyriders.isEmpty()) {
	        throw new RuntimeException("No nearby riders found");
	    }


	    // FIRST save booking so that booking ID is generated
	    Booking savedbooking = bookingrepo.save(booking);


	    // Create only the data that rider needs
	    Map<String, String> riderRequest = new HashMap<>();

	    riderRequest.put(
	            "bookingId",
	            String.valueOf(savedbooking.getId())
	    );

	    riderRequest.put(
	            "customerId",
	            String.valueOf(id)
	    );

	    riderRequest.put(
	            "sourceLat",
	            data.get("sourceLat").toString()
	    );

	    riderRequest.put(
	            "sourceLon",
	            data.get("sourceLon").toString()
	    );

	    riderRequest.put(
	            "destinationLat",
	            data.get("destinationLat").toString()
	    );

	    riderRequest.put(
	            "destinationLon",
	            data.get("destinationLon").toString()
	    );

	    riderRequest.put(
	            "distance",
	            data.get("distance").toString()
	    );

	    riderRequest.put(
	            "vehicleType",
	            vehtype
	    );


	    // Send request to each nearby rider
	    for (String riderId : nearbyriders) {

	        String key = "riderrequest:" + riderId;

	        redistemplate.opsForHash()
	                     .putAll(key, riderRequest);

	        redistemplate.expire(
	                key,
	                Duration.ofDays(2)
	        );
	    }

	    return savedbooking;
	}

	public String acceptedbooking(int rid, int bid) {
		Booking booking=bookingrepo.findById(bid).orElseThrow(()->new BookingnotfoundException());
		 if (!booking.getBstatus().equals("pending")) {
		        return "Ride already accepted";
		    }
		booking.setRiderid(rid);
		booking.setBstatus("rider assigned");
		bookingrepo.save(booking);
		String url ="http://localhost:8089/rider/updateacceptedridestatus"+"?rid="+rid+"&bid"+bid;
		return resttemplate.postForObject(  url,  null,  String.class )+"RIde Accepted";
	}
	
	//###################################################################################################################################
	
	public AcceptBookingResponseDto assignRiderService(int riderid, int bookingid) {

		Booking booking = bookingrepo.findById(bookingid).orElseThrow(() -> new BookingnotfoundException());

		// Check whether another rider already accepted
		if (!booking.getBstatus().equalsIgnoreCase("pending")) {
			throw new RiderAlreadyAcceptedException();
		}

		// Assign rider
		booking.setRiderid(riderid);
		booking.setBstatus("riderAssigned");

		bookingrepo.save(booking);

		// Get customer associated with this booking
		Customer customer = booking.getCustomers();

		AcceptBookingResponseDto response = new AcceptBookingResponseDto();

		response.setBookingid(booking.getId());
		response.setFare(booking.getFare());

		response.setPickuplocation(booking.getPickup());
		response.setCustomername(customer.getCustomername());
		response.setCustomermobile(customer.getCustomermobno());
		response.setDistance(booking.getDistance());

		return response;
	}

	
	public String moveTowardsPickupService(int bookingid) {

		Booking booking = bookingrepo.findById(bookingid).orElseThrow(() -> new BookingnotfoundException());

		Coordinates pickup = booking.getSource();

		if (pickup == null) {
			throw new RuntimeException("Pickup coordinates not found");
		}

		String url = "https://www.google.com/maps/dir/?api=1" + "&destination=" + pickup.getLatittude() + ","
				+ pickup.getLongitude() + "&travelmode=driving";

		return url;
	}

	public String updateToArrivedService(int bookingid) {
		Booking booking = bookingrepo.findById(bookingid).orElseThrow(() -> new BookingnotfoundException());
		booking.setBstatus("riderReachedPickup");
		bookingrepo.save(booking);
		return "rider has reached to pickup location";
	}

	public VerifyOtpResponseDto verifyOtpService(int bookingid, String otp) {

		Booking booking = bookingrepo.findById(bookingid).orElseThrow(() -> new BookingnotfoundException());

		Customer customer = booking.getCustomers();

		if (customer == null) {
			throw new RuntimeException("Customer not found");
		}

		// OTP from database
		String actualOtp = customer.getCustomerotp();

		// Convert API int OTP to String
		String enteredOtp = String.valueOf(otp);
		
		VerifyOtpResponseDto response = new VerifyOtpResponseDto();

		if (actualOtp.equals(enteredOtp)) {

			booking.setBstatus(enteredOtp);
			LocalTime now = LocalTime.now();
		    booking.setPickuptime(now.toString());

			bookingrepo.save(booking);

			response.setVerified(true);
			response.setMessage("OTP verified successfully");

		} else {

			response.setVerified(false);
			response.setMessage("Invalid OTP");
		}

		return response;
	}

	public String moveTowardsDropService(int bookingid) {
		Booking booking = bookingrepo.findById(bookingid).orElseThrow(() -> new BookingnotfoundException());

		Coordinates pickup = booking.getDestination();

		if (pickup == null) {
			throw new RuntimeException("Destination coordinates not found");
		}

		String url = "https://www.google.com/maps/dir/?api=1" + "&destination=" + pickup.getLatittude() + ","
				+ pickup.getLongitude() + "&travelmode=driving";

		return url;
	}

	public String updateToReachedService(int bookingid) {
		Booking booking = bookingrepo.findById(bookingid).orElseThrow(() -> new BookingnotfoundException());
		booking.setBstatus("riderReachedDestination");
		bookingrepo.save(booking);
		return "rider has reached to Drop location";
	}

	public CompleteRideResponseDto completeRideService(int bookingid) {

		Booking booking = bookingrepo.findById(bookingid).orElseThrow(() -> new BookingnotfoundException());

		// Update booking status
		booking.setBstatus("completed");

		// Update payment status
		booking.setPaymentstatus("completed");
		LocalTime now = LocalTime.now();
	    booking.setDroptime(now.toString());
	    
		Booking savedBooking = bookingrepo.save(booking);
		
		
		LocalTime pickupTime = LocalTime.parse(booking.getPickuptime());
		LocalTime dropTime = LocalTime.parse(booking.getDroptime());

		double duration = Duration.between(pickupTime, dropTime).toMinutes();

		booking.setDuration(String.valueOf(duration));
		bookingrepo.save(booking);

		// Create response
		CompleteRideResponseDto response = new CompleteRideResponseDto(); 

		response.setBookingid(savedBooking.getId());
		response.setRiderid(savedBooking.getRiderid());
		response.setFare(savedBooking.getFare());
		response.setStatus(savedBooking.getBstatus());
		response.setPaymentstatus(savedBooking.getPaymentstatus());

		return response;
	}

	
	//******************************************************************************
	public List<Booking> rideHistoryService(int customerid, String status) {

		if (status.equalsIgnoreCase("all")) {

			return bookingrepo.findByCustomerId(customerid);

		} else if (status.equalsIgnoreCase("completed")) {

			return bookingrepo.findByCustomerIdAndStatus(customerid, "completed");

		} else if (status.equalsIgnoreCase("canceled")) {

			return bookingrepo.findByCustomerIdAndStatus(customerid, "customercancel");

		} else {

			throw new IllegalArgumentException("Invalid status. Use all, completed or canceled");
		}
	}

	public List<RiderHistoryDto> riderHistoryService(int riderid, String status) {

		List<Booking> bookings;

		if (status.equalsIgnoreCase("all")) {

			bookings = bookingrepo.findByRiderid(riderid);

		} else if (status.equalsIgnoreCase("completed")) {

			bookings = bookingrepo.findByRideridAndStatus(riderid, "completed");

		} else if (status.equalsIgnoreCase("ridercancel")) {

			bookings = bookingrepo.findByRideridAndStatus(riderid, "ridercancel");

		} else {

			throw new IllegalArgumentException("Invalid status. Use all, completed or ridercancel");
		}

		List<RiderHistoryDto> history = new ArrayList<>();

		for (Booking booking : bookings) {

			RiderHistoryDto dto = new RiderHistoryDto();

			dto.setBookingid(booking.getId());
			dto.setPickup(booking.getPickup());
			dto.setDrop(booking.getDrop());
			dto.setVehicle(booking.getVehciletype());
			dto.setFare(booking.getFare());
			dto.setDistance(booking.getDistance());
			dto.setBookingdate(booking.getBookingdate());
			dto.setBookingtime(booking.getBookingtime());
			dto.setDuration(booking.getDuration());
			dto.setStatus(booking.getBstatus());
			dto.setPaymentstatus(booking.getPaymentstatus());

			history.add(dto);
		}

		return history;
	}

	public EarningSummaryDto earningSummaryService(int riderid, String from, String to) {

		List<Booking> bookings = bookingrepo.findByRideridAndStatusAndBookingdateBetween(riderid, "completed",
				from, to);

		int noofrides = bookings.size();

		double ridershare = 0;
		double totaldistance = 0;

		for (Booking booking : bookings) {

			ridershare += booking.getRidershare();

			totaldistance += booking.getDistance();
		}

		EarningSummaryDto response = new EarningSummaryDto();

		response.setNoofrides(noofrides);
		response.setRidershare(ridershare);
		response.setTotaldistance(totaldistance);

		return response;
	}
}

	


