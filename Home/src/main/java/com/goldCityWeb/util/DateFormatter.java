package com.goldCityWeb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

public class DateFormatter
{
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public String getCurrentDate()
	{
		return new DateTime(new Date()).toString(DATE_FORMAT);
	}
	
	public String formatDateToStr(Date date) {
		return formatDateToStr(date, DATE_FORMAT);
	}
	
	public String formatDateToStr(Date date, String format) {
		if (date == null) {
			return "";
		}
		return new DateTime(date).toString(format);
	}
	
	/**
	 * @param dt
	 * @return
	 */
	public static Date stringToDate(String dt) {
		return stringToDate(dt, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static Date stringToDate(String dt, String format) {
		if (!StringUtils.isBlank(dt)) {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			try {
				return formatter.parse(dt);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @param dt
	 * @param formater
	 * @return
	 */
	public static String dateToString(Date dt, String formater) {
		if (StringUtils.isBlank(formater))
			formater = "yyyy-MM-dd HH:mm:ss";
		if (dt != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(formater);
			return formatter.format(dt);
		}
		return null;
	}
}
