package com.inalab.util;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {

	private static final Log log = LogFactory.getLog(DateUtil.class);

	public DateUtil() {
	}

	public static String getCurrentDateTime() {

		java.util.Date now = new java.util.Date();

		SimpleDateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String sqlDate = SQL_DATE_FORMAT.format(now);

		return sqlDate;
	}

	public static String getCurrentDateTime(String format, String timezone) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));

		return dateFormat.format(new java.util.Date());
	}

	public static String getDisplayDate(String actionDate) {

		if (actionDate == null || ("").equals(actionDate))
			actionDate = getCurrentDateTime();
		// 2008-12-02
		String year = actionDate.substring(0, 4);
		String month = actionDate.substring(5, 7);
		String date = actionDate.substring(8, 10);

		return month + "/" + date + "/" + year;
	}

	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	public static String currentDate() {

		Calendar cal = Calendar.getInstance();

		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);

		String retDate = convertToDoubleDigit(month + 1) + "/" + convertToDoubleDigit(day) + "/"
				+ new Integer(year).toString();

		return retDate;

	}

	public static String convertToBackEndFormat(String date) {

		Calendar newCal = convertToCalendar(date);
		newCal.set(Calendar.HOUR, 0);
		newCal.set(Calendar.MINUTE, 0);
		newCal.set(Calendar.SECOND, 0);

		SimpleDateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String sqlDate = SQL_DATE_FORMAT.format(newCal.getTime());

		return sqlDate;
	}

	public static String decrementDay(String date) {
		Calendar newCal = convertToCalendar(date);
		// log.debug(newCal.getTime());
		newCal.add(Calendar.DATE, -1);
		// log.debug(newCal.getTime());
		int day = newCal.get(Calendar.DATE);
		int month = newCal.get(Calendar.MONTH);
		int year = newCal.get(Calendar.YEAR);

		String retDate = convertToDoubleDigit(month + 1) + "/" + convertToDoubleDigit(day) + "/"
				+ new Integer(year).toString();

		return retDate;
	}

	public static String incrementDay(String date) {

		Calendar newCal = convertToCalendar(date);
		// log.debug(newCal.getTime());
		newCal.add(Calendar.DATE, 1);
		// log.debug(newCal.getTime());
		int day = newCal.get(Calendar.DATE);
		int month = newCal.get(Calendar.MONTH);
		int year = newCal.get(Calendar.YEAR);

		String retDate = convertToDoubleDigit(month + 1) + "/" + convertToDoubleDigit(day) + "/"
				+ new Integer(year).toString();

		return retDate;
	}

	public static Date getTomorrow12AM(Date date) {
		Calendar newCal = Calendar.getInstance();
		newCal.setTime(date);
		newCal.add(Calendar.DATE, 1);
		newCal.set(Calendar.HOUR, 00);
		newCal.set(Calendar.MINUTE, 00);
		return newCal.getTime();

	}

	private static Calendar convertToCalendar(String date) {

		date = date.trim();

		Calendar newCal = Calendar.getInstance();
		int month = new Integer(date.substring(0, 2)).intValue();
		int day = new Integer(date.substring(3, 5)).intValue();
		int year = new Integer(date.substring(6)).intValue();

		newCal.set(Calendar.DATE, day);
		newCal.set(Calendar.MONTH, month - 1);
		newCal.set(Calendar.YEAR, year);

		return newCal;

	}

	public static String getLocalTime(String zoneStr, String dstStr, Locale locale, String publishingTime) {
		try {

			boolean plusZoneOffset = false;
			boolean plusDstOffset = false;

			if (dstStr.startsWith("+"))
				plusDstOffset = true;

			if (zoneStr.startsWith("+"))
				plusZoneOffset = true;

			dstStr = dstStr.substring(1);
			if (dstStr.startsWith("0"))
				dstStr = dstStr.substring(1);

			zoneStr = zoneStr.substring(1);
			if (zoneStr.startsWith("0"))
				zoneStr = zoneStr.substring(1);

			zoneStr = zoneStr.replace(":", ".");
			dstStr = dstStr.replace(":", ".");

			int dst = (new Double(dstStr).intValue());
			int zone = (new Double(zoneStr).intValue());
			if (!plusDstOffset)
				dst = -dst;

			if (!plusZoneOffset)
				zone = -zone;

			Calendar gmtC = Calendar.getInstance();
			SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date publishRequestedDate1 = simpleDateFormat3.parse(publishingTime);
			gmtC.setTime(publishRequestedDate1);

			gmtC.add(Calendar.HOUR, (dst + zone));
			log.debug(dst + "  " + zone + "  " + plusDstOffset + "  " + plusZoneOffset);
			log.debug(
					zoneStr + "  " + dstStr + "  " + publishingTime + "  " + simpleDateFormat3.format(gmtC.getTime()));

			return simpleDateFormat3.format(gmtC.getTime());
		} catch (Exception ex) {
			return publishingTime;
		}
	}

	public static String getGMTTime(String zoneStr, String dstStr, Locale locale, String publishingTime) {
		try {

			boolean plusZoneOffset = false;
			boolean plusDstOffset = false;

			if (dstStr == null || ("").equals(dstStr))
				dstStr = "-05.00";

			if (zoneStr == null || ("").equals(zoneStr))
				zoneStr = "+01:00";

			if (dstStr.startsWith("+"))
				plusDstOffset = true;

			if (zoneStr.startsWith("+"))
				plusZoneOffset = true;

			SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date publishRequestedDate = simpleDateFormat2.parse(publishingTime);

			// create a new calendar in GMT timezone, set to this date and add
			// the offset
			Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			gmtCal.setTime(publishRequestedDate);
			dstStr = dstStr.substring(1);
			if (dstStr.startsWith("0"))
				dstStr = dstStr.substring(1);

			zoneStr = zoneStr.substring(1);
			if (zoneStr.startsWith("0"))
				zoneStr = zoneStr.substring(1);

			zoneStr = zoneStr.replace(":", ".");
			dstStr = dstStr.replace(":", ".");
			int dst = (new Double(dstStr).intValue()) * 60 * 60 * 1000;
			int zone = (new Double(zoneStr).intValue()) * 60 * 60 * 1000;
			if (plusDstOffset)
				gmtCal.set(Calendar.DST_OFFSET, dst);
			else
				gmtCal.set(Calendar.DST_OFFSET, -dst);

			if (plusZoneOffset)
				gmtCal.set(Calendar.ZONE_OFFSET, zone);
			else
				gmtCal.set(Calendar.ZONE_OFFSET, -zone);
			String time = simpleDateFormat2.format(gmtCal.getTime());
			log.debug("Created Offset cal with date [" + time + "]");

			return time;
		} catch (Exception ex) {
			log.error("Error ", ex);
			return publishingTime;
		}
	}

	public static Date getGMTToday() {
		Calendar newCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		return newCal.getTime();

	}

	public static Date getDateOnly(Date dt) {
		Calendar cal = Calendar.getInstance(); // locale-specific
		cal.setTime(dt);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date addMinutes(Date inputDate, int minutes) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.setTime(inputDate);
		cal.add(Calendar.MINUTE, minutes);// Add 10 minutes

		return cal.getTime();
	}

	private static String convertToDoubleDigit(int a) {
		String ret = "0";
		if (a < 10)
			ret = ret + a;
		else
			ret = new Integer(a).toString();

		return ret;
	}

	public static long timeDiffInSeconds(Date lastRefresh) {
		return (System.currentTimeMillis() - lastRefresh.getTime()) / 1000L;
	}

	public static void main(String[] args) throws Exception {

		// log.debug(DateUtil.getCurrentDateTime());

		// String date = "03/12/2009 ";
		// log.debug("Input Date = " + date);
		// log.debug("Decrement = " + DateUtil.decrementDay(date));
		// log.debug("Increment = " + DateUtil.incrementDay(date));
		// log.debug("Backend = " + DateUtil.convertToBackEndFormat(date));
		// log.debug("Today = " + DateUtil.currentDate());
		// log.debug("Front = "
		// + DateUtil
		// .getDisplayDate(DateUtil.convertToBackEndFormat(date)));

		// String a = "+5.00";
		// String b = "-1.00";
		//
		// System.out
		// .println(new Double(a.substring(1)).doubleValue() * 60 * 60 * 1000);
		// System.out
		// .println(new Double(b.substring(1)).doubleValue() * 60 * 60 * 1000);
		//
		// // GMT time to local time
		// Calendar c = Calendar.getInstance();
		// SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(
		// "MM/dd/yyyy HH:mm");
		// Date publishRequestedDate = simpleDateFormat2.parse("08/26/2012
		// 13:00");
		//
		// // create a new calendar in GMT timezone, set to this date and add
		// the
		// // offset
		// Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		// gmtCal.setTime(publishRequestedDate);
		// int dst = (new Double(b.substring(1)).intValue()) * 60 * 60 * 1000;
		// int zone = (new Double(a.substring(1)).intValue()) * 60 * 60 * 1000;
		// gmtCal.set(Calendar.DST_OFFSET, (dst));
		// gmtCal.set(Calendar.ZONE_OFFSET, -zone);
		// String time = simpleDateFormat2.format(gmtCal.getTime());
		// System.out.println("Created Offset cal with date [" + time + "]");
		//
		// // local to GMT
		//
		// Calendar gmtC = Calendar.getInstance();
		// SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat(
		// "MM/dd/yyyy HH:mm");
		// Date publishRequestedDate1 = simpleDateFormat3.parse(time);
		// gmtC.setTime(publishRequestedDate1);
		// System.out.println(dst + zone);
		// gmtC.add(Calendar.HOUR, (-dst + zone) / (60 * 60 * 1000));
		// System.out.println(simpleDateFormat3.format(gmtC.getTime()));

		// System.out.println(DateUtil.getLocalTime("-05:00", "+01:00", null,
		// "08/26/2012 21:25") );
		String d = "09/15/2012 22:52";

		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM/dd/yyyy HH:mm");

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		System.out.println("GMT=" + dateFormat.format(new java.util.Date()));
		// dateFormat.setTimeZone(TimeZone.getDefault());

		Date publishRequestedDate = simpleDateFormat2.parse(d);

		// create a new calendar in GMT timezone, set to this date and add
		// the offset
		Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		// gmtCal.setTime(now);

		System.out.println("server time in GMT=" + gmtCal.getTime());

		TimeZone defaultTZ = TimeZone.getDefault();
		System.out.println(defaultTZ.getOffset(Calendar.DST_OFFSET));
		System.out.println(defaultTZ.getOffset(Calendar.ZONE_OFFSET));

		gmtCal.set(Calendar.DST_OFFSET, defaultTZ.getOffset(Calendar.DST_OFFSET) / (60 * 60 * 1000));
		gmtCal.set(Calendar.ZONE_OFFSET, defaultTZ.getOffset(Calendar.ZONE_OFFSET) / (60 * 60 * 1000));

		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

		// Local time zone
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

		System.out.println(dateFormatLocal.parse(dateFormatGmt.format(new Date())));
		System.out.println(gmtCal.getTime());
		System.out.println(publishRequestedDate);
	}
}
