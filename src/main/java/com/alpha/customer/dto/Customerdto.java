package com.alpha.customer.dto;

public class Customerdto {
	
	private String name;
	private long mobno;
	private String mail;
	private String gender;
	private int otp;
	public Customerdto(String name, long mobno, String mail, String gender) {
		super();
		this.name = name;
		this.mobno = mobno;
		this.mail = mail;
		this.gender = gender;
	}
	public Customerdto() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getMobno() {
		return mobno;
	}
	public void setMobno(long mobno) {
		this.mobno = mobno;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getOtp() {
		return otp;
	}
	public void setOtp(int otp) {
		this.otp = otp;
	}
	
	
}
