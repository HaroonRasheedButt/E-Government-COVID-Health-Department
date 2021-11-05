package com.example.EGovt_CovidHealthApp.Model.Entity;

import java.util.Date;

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
	private Date createdDate;
	@Column
	private Date verifyingDate;
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
	public Date getverifyingDate() {
		return verifyingDate;
	}
	public void setverifyingDate(Date expiryDate) {
		this.verifyingDate = expiryDate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
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
