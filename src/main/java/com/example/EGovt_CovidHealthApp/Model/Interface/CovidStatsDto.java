package com.example.EGovt_CovidHealthApp.Model.Interface;

import java.util.Date;

public class CovidStatsDto {
	String country;
	String province;
	String city;
	Date after;
	Date before;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getAfter() {
		return after;
	}
	public void setAfter(Date after) {
		this.after = after;
	}
	public Date getBefore() {
		return before;
	}
	public void setBefore(Date before) {
		this.before = before;
	}

	
}
