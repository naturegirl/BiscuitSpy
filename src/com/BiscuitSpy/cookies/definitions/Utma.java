package com.BiscuitSpy.cookies.definitions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class Utma {

	// utma cookie structure:
	// 'domain hash'.'visitor id'.'initial visit'.'previous visit'.'current visit'.'session number'
	String domainhash, visitorid, sessionCount;
	String firstVisit, previousVisit, currentVisit;	// saved in date time format: "01/16/1970 08:55 PM"
	
	/**
	 * 
	 * @param utmastring: the value of the utma cookie.
	 */
	public Utma(String utmastring) {
		if (utmastring == null || utmastring == "") {
			System.out.println("Error in Utma(): utmastring is empty");
			return;
		}
		String delim = ".";
		StringTokenizer st = new StringTokenizer(utmastring, delim);
		if (st.countTokens() != 6) {
			System.out.println("Error in Utma(): utma tokens count incorrect");
			return;
		}
		domainhash = st.nextToken();
		visitorid = st.nextToken();
		firstVisit = convertTimestamp(st.nextToken());
		previousVisit = convertTimestamp(st.nextToken());
		currentVisit = convertTimestamp(st.nextToken());
		sessionCount = st.nextToken();
	}
	
	/**
	 * 
	 * @param unixTimestamp: unix timestamp (in seconds, not milliseconds)
	 * @return returns a time string of format: "01/16/1970 08:55 PM"
	 */
	private String convertTimestamp(String unixTimestamp) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		long timestamp = Long.parseLong(unixTimestamp) * 1000;	// miliseconds
		Date date = new Date(timestamp);
		return df.format(date);
	}
	
	public String getVisitorId() {
		return visitorid;
	}
	
	public String getFirstVisit() {
		return firstVisit;
	}
	
	public String getPreviousVisit() {
		return previousVisit;
	}

	public String getCurrentVisit() {
		return currentVisit;
	}
	
	public String getSessionCount() {
		return sessionCount;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String utmastring = "1.730730778.1389315522.1389315888.1389361886.3";
		Utma utma = new Utma(utmastring);
		System.out.println(utma.getFirstVisit());
		System.out.println(utma.getPreviousVisit());
		System.out.println(utma.getCurrentVisit());
	}

}
