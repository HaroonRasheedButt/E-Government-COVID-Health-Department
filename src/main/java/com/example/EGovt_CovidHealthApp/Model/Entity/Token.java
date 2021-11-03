package com.example.EGovt_CovidHealthApp.Model.Entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private long userId;
	@Column(nullable = false)
	private String userType;
	@Column(nullable = false)
	private Timestamp createdDate;
	@Column
	private Timestamp verifyingDate;
	@Column(nullable = false)
	private String smsToken;
	@Column(nullable = false)
	private String emailToken;
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Timestamp getverifyingDate() {
		return verifyingDate;
	}
	public void setverifyingDate(Timestamp expiryDate) {
		this.verifyingDate = expiryDate;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getSmsToken() {
		return smsToken;
	}
	public void setSmsToken(String smsToken) {
		this.smsToken = smsToken;
	}
	public String getEmailToken() {
		return emailToken;
	}
	public void setEmailToken(String emailToken) {
		this.emailToken = emailToken;
	}

}
