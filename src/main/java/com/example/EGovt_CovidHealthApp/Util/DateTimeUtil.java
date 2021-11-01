package com.example.EGovt_CovidHealthApp.Util;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

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

}
