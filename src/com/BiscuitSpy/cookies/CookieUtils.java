package com.BiscuitSpy.cookies;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class CookieUtils {
	
	// Cookie name constants
	public static final String UTMA = "__utma";
	public static final String UTMB = "__utmb";
	public static final String UTMC = "__utmc";
	public static final String UTMZ = "__utmz";
	public static final String PREF = "PREF";
	public static final String DBCLK_ID = "id";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String cookies = "clid=2mz5s57011708l6h8vrnywmn004a200809080208108; sglst=41z900bf031liw0o0013z0o000td0o0; ipinfo=2mz5s570zik0bggxe0rxzg0mkt72AW553095HF9CevaprgbajCevaprgbaHavirefvgldcevaprgba.rqh0; acs=019020e0f0h0i0j0k0n0o1mz5s57xzt112gxzt112gxzt112gxzt14a2xzt10xzt10xzt10xzt14a2; vstcnt=41z9010r014sr6g127p1002; rdrlst=40428gamz5t8400000004090428tdmz5wf90000000109012aiimz5t8p0000000209022a0amz5t7n000000050905";
		tokenize(cookies,";");
	}
	/**
	 * Tokenize the cookies string to return Strings for the individual cookies.
	 * @param cookies the string all the cookies from the http COOKIE field.
	 */
	public static ArrayList<String> tokenize(String cookies, String delim) {
		if (cookies == null || cookies == "")
			return new ArrayList<String>();
		//String delim = "; ";
		StringTokenizer st = new StringTokenizer(cookies, delim);
		ArrayList<String> result = new ArrayList<String>();
		while (st.hasMoreTokens())
			result.add(st.nextToken());
		for (String s : result)
			System.out.println(s);
		return result;
	}	
	
	/**
	 * converts a unix timestamp to a readable date time string 
	 * @param unixTimestamp: unix timestamp (in seconds, not milliseconds)
	 * @return returns a time string of format: "01/16/1970 08:55 PM"
	 */
	public static String convertTimestamp(String unixTimestamp) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		long timestamp = Long.parseLong(unixTimestamp) * 1000;	// miliseconds
		Date date = new Date(timestamp);
		return df.format(date);
	}
	
}
