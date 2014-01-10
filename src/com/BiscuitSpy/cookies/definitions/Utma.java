package com.BiscuitSpy.cookies.definitions;

import java.util.StringTokenizer;
import com.BiscuitSpy.cookies.CookieUtils;

public class Utma {

	// cookie name: __utma
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
		firstVisit = CookieUtils.convertTimestamp(st.nextToken());
		previousVisit = CookieUtils.convertTimestamp(st.nextToken());
		currentVisit = CookieUtils.convertTimestamp(st.nextToken());
		sessionCount = st.nextToken();
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
