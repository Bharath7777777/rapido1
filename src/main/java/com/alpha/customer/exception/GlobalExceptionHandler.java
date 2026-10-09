package com.alpha.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alpha.customer.dto.Customerdto;
import com.alpha.customer.dto.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(CustomernotFoundException.class)
	public ResponseStructure<String> customernotfoundexception() {
		ResponseStructure<String> res=new ResponseStructure();
		res.setMessage("Customer Not found Exceptionf");
		res.setData(null);
		res.setStatuscode(HttpStatus.NOT_FOUND.value());
		return res;
	}
	
	@ExceptionHandler(BookingnotfoundException.class)
	public ResponseStructure<String> BookingnotfoundException() {
		ResponseStructure<String> res=new ResponseStructure();
		res.setMessage("Booking Not found Exception");
		res.setData(null);
		res.setStatuscode(HttpStatus.NOT_FOUND.value());
		return res;
	}
	@ExceptionHandler(RiderAlreadyAcceptedException.class)
	public ResponseStructure<String> RiderAlreadyAcceptedException() {
		ResponseStructure<String> res=new ResponseStructure();
		res.setMessage("Rider Already accepted Exception");
		res.setData(null);
		res.setStatuscode(HttpStatus.NOT_FOUND.value());
		return res;
}
}
