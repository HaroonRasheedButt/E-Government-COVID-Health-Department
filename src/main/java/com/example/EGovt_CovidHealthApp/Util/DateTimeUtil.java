package com.example.EGovt_CovidHealthApp.Util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

	/**
	 * Returns the current date of type java.util.Date
	 * @return current date and time of type java.util.Date
	 */
	public static java.util.Date getDate() {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		try {
			date = formatter.parse(formatter.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;	
	}

	/**
	 * Returns the current date&time of type java.sql.Timestamp
	 * @return date and time of type java.sql.Timestamp
	 */
	public static Timestamp getTimestamp() {
		java.util.Date date = new java.util.Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}
	
	/**
	 * Formats the date of type java.util.Date
	 * @return formatted date and time of type java.util.Date
	 */
	public static java.util.Date getFormat(Date date) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			date = formatter.parse(formatter.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;	
	}
	
}
