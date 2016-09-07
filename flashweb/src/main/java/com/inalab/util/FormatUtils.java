package com.inalab.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.HtmlUtils;

public class FormatUtils {
	public static final String DISPLAY_DATE_FORMAT = "dd-MMM-yyyy";
	public static final String SCHEDULE_DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.SSS";
	public static final String UTC_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String GOOG_ANALYTICS_DATE_FORMAT = "yyyy-mm-dd";

	public static final String toDisplayDate(Date dbDate) {
		if (dbDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
			return sdf.format(dbDate);
		}

		return "";
	}

	public static final String toDisplayUTCScheduleDate(Date dbDate) {
		if (dbDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(UTC_FORMAT);
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			return sdf.format(dbDate) + " UTC";
		}

		return "";
	}

	public static final String toDisplayScheduleDate(Date dbDate) {
		if (dbDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(SCHEDULE_DATE_FORMAT);
			return sdf.format(dbDate) + 'Z';
		}

		return "";
	}

	public static final Date toDate(String str) {
		if (str != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
				return sdf.parse(str);
			} catch (ParseException pe) {

			}
		}

		return null;
	}

	public static String textToHtmlConvertingURLsToLinks(String text) {
		if (text == null) {
			return text;
		}

		String escapedText = HtmlUtils.htmlEscape(text);

		return escapedText.replaceAll("(\\A|\\s)((http|https|ftp|mailto):\\S+)(\\s|\\z)", "$1<a href=\"$2\">$2</a>$4");
	}

	public static String getValue(String key, String queryString) {
		if (key == null)
			return null;

		String value = null;
		if (StringUtils.isNotEmpty(queryString) && queryString.contains(key)) {
			String keyValues[] = queryString.split("&");
			if (keyValues != null && keyValues.length > 0) {
				for (String keyValue : keyValues) {
					if (keyValue.contains(key)) {
						return keyValue.substring(key.length() + 1);
					}
				}
			}
		}

		return value;
	}

	public static void main(String[] args) {
		System.out.println(FormatUtils.toDisplayScheduleDate(Calendar.getInstance().getTime()));
	}
}
