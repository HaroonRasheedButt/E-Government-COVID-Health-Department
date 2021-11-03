package com.example.EGovt_CovidHealthApp.Util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;

public class DateTimeUtil {

	private static final String DATE_FORMAT = "dd-M-yyyy hh:mm:ss z";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final ZoneId toTimeZone = ZoneId.of("Asia/Karachi");  //Target timezone
	/**
	 * Returns the current date of type java.sql.Date 
	 * @return date of type java.sql.Date
	 */
	public static java.sql.Date getDate() {
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
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
	 * Returns the current date&time of type java.sql.LocalDateTime
	 * @return date and time of type java.sql.Timestamp
	 */
	public static LocalDateTime getLocalDateTime() {
		LocalDateTime dateTime = LocalDateTime.now();
		dateTime = LocalDateTime.parse(formatter.format(dateTime));
		
		return dateTime;
	}
	
//	public static LocalDateTime getSystemDefaultTimeZone(LocalDateTime dateTime) {	
//		dateTime.atZone(toTimeZone);
//        //Zoned date time at target timezone
//        ZonedDateTime currentETime = currentISTime.withZoneSameInstant(toTimeZone);
//		
//		return currentETime;
//	}

}
