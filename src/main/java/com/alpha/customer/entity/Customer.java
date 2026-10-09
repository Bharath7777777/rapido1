package com.alpha.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int custid;
	private String customername;
	@Column(unique = true)
	private long customermobno;
	@Column(unique = true)
	private String customermail;
	private String customergender;
	@Column(unique = true)
	private String customerotp;
	public Customer(String customername, long customermobno, String customermail, String customergender,
			String customerotp) {
		super();
		this.customername = customername;
		this.customermobno = customermobno;
		this.customermail = customermail;
		this.customergender = customergender;
		this.customerotp = customerotp;
	}
	public Customer() {
		super();
	}
	public int getCustid() {
		return custid;
	}
	public void setCustid(int custid) {
		this.custid = custid;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public long getCustomermobno() {
		return customermobno;
	}
	public void setCustomermobno(long customermobno) {
		this.customermobno = customermobno;
	}
	public String getCustomermail() {
		return customermail;
	}
	public void setCustomermail(String customermail) {
		this.customermail = customermail;
	}
	public String getCustomergender() {
		return customergender;
	}
	public void setCustomergender(String customergender) {
		this.customergender = customergender;
	}
	public String getCustomerotp() {
		return customerotp;
	}
	public void setCustomerotp(String customerotp) {
		this.customerotp = customerotp;
	}
	
	
}
