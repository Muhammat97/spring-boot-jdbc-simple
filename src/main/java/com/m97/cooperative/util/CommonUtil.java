package com.m97.cooperative.util;

import java.util.Base64;
import java.util.TimeZone;

public class CommonUtil {

	public static final String REGEX_ONLY_NUMBER = "^[0-9]+$";

	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final String PATTERN_TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ss";

	public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT+7");

	public static String getUsernameByAuth(String auth) {
		auth = auth.replaceAll("(?i)Basic\\s+", "");
		String decoded = new String(Base64.getDecoder().decode(auth));
		return decoded.split(":")[0];
	}

}
