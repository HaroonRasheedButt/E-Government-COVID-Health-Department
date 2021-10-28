package com.example.EGovt_CovidHealthApp;

import java.sql.Date;

public class DateTime {
	
	public static Date getDate() {
		long millis = System.currentTimeMillis();
		Date date = new Date(millis);
		return date;
	}
}
