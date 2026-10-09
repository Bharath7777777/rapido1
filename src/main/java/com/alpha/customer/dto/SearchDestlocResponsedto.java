package com.alpha.customer.dto;

public class SearchDestlocResponsedto {
	private String address;
	private String name;
	public SearchDestlocResponsedto(String address, String name) {
		super();
		this.address = address;
		this.name = name;
	}
	public SearchDestlocResponsedto() {
		super();
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
