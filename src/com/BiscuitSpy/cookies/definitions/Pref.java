package com.BiscuitSpy.cookies.definitions;

import java.util.StringTokenizer;

import com.BiscuitSpy.cookies.Cookie;
import com.BiscuitSpy.cookies.CookieUtils;

public class Pref extends Cookie{

	// cookie name: PREF. (only from google and *.google.com domain)
	// PREF	 cookie structure:
	// fields separated by ':' where each field has structure key=value
	// i.e.: ID=6261a36466dd8b7e:U=9154fd11459bcaf3:FF=0:LD=en:TM=1389303115:LM=1389303949:GM=1:S=0QQQfi6_B0mI80LB
	// TM: creation time, LM: last modified time

	final static String DELIM = ":";
	final static int FIELD_COUNT = 8;
	
	String id;	// browser/user identifier
	String language;
	String creationTime, lastModifiedTime;	// from TM and LM. Safe in human-readable format
	
	public Pref(String pref_string) {
		super(pref_string);
		if (pref_string == null || pref_string == "") {
			System.out.println("Error in Pref(): pref_string is empty");
			return;
		}
		StringTokenizer st = new StringTokenizer(pref_string, DELIM);
		if (st.countTokens() != FIELD_COUNT) {
			System.out.println("Error in Pref(): wordpress cookie tokens count incorrect");
			return;
		}
		id = CookieUtils.getFieldValue(st.nextToken());
		st.nextToken();	st.nextToken(); 	// skip U and FF
		language = CookieUtils.getFieldValue(st.nextToken());
		creationTime = CookieUtils.convertTimestamp(
				CookieUtils.getFieldValue(st.nextToken()));
		lastModifiedTime = CookieUtils.convertTimestamp(
				CookieUtils.getFieldValue(st.nextToken()));
	}
	
	public String getBrowserId() {
		return id;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public String getCreationTime() {
		return creationTime;
	}
	
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String pref_string = "ID=6261a36466dd8b7e:U=9154fd11459bcaf3:FF=0:LD=en:TM=1389303115:LM=1389303949:GM=1:S=0QQQfi6_B0mI80LB";
		Pref pref = new Pref(pref_string);
		System.out.println("hello world");
	}

}
