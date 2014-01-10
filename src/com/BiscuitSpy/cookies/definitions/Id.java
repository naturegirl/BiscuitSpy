package com.BiscuitSpy.cookies.definitions;

import java.util.StringTokenizer;

import com.BiscuitSpy.cookies.CookieUtils;

public class Id {

	// cookie name: id (only from *.doubleclick.net domain)
	// id cookie structure:
	// fields separated by '|' where *apart from the first 2 fields* each field has structure key=value
	// i.e.: 22ffdcab7e02005f||t=1389303219|et=730|cs=002213fd4891bd21ca8019cd30
	
	final static String DELIM = "|";
	final static int FIELD_COUNT = 5;
	
	String adnumbers;	// second field of format a/b/c/... believe it's saying something about which ads to show
	String timestamp;
	String et;		// meaning?
	String cs;		// meaning?
	
	public Id(String id_string) {
		if (id_string == null || id_string == "") {
			System.out.println("Error in Id(): id_string is empty");
			return;
		}
		StringTokenizer st = new StringTokenizer(id_string, DELIM);
		
		if (st.countTokens() != FIELD_COUNT && st.countTokens() != FIELD_COUNT-1) {
			System.out.println("Error in Id(): wordpress cookie tokens count incorrect");
			return;
		}
		
		if (st.countTokens() == FIELD_COUNT) {
			st.nextToken();
			adnumbers = st.nextToken();
			timestamp = CookieUtils.convertTimestamp(
					CookieUtils.getFieldValue(st.nextToken()));
			et = CookieUtils.getFieldValue(st.nextToken());
			cs = CookieUtils.getFieldValue(st.nextToken());			
		}
		
		// sometimes the second field is empty. Not able to parse empty field, so I'm handling it seperately.
		else if (st.countTokens() == FIELD_COUNT-1) {
			st.nextToken();
			timestamp = CookieUtils.convertTimestamp(
					CookieUtils.getFieldValue(st.nextToken()));
			et = CookieUtils.getFieldValue(st.nextToken());
			cs = CookieUtils.getFieldValue(st.nextToken());
		}
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public String getAdnumbers() {
		return adnumbers;
	}
	
	public String getET() {
		return et;
	}

	public String getCS() {
		return cs;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String id_string = "22ffdcab7e02005f||t=1389303219|et=730|cs=002213fd4891bd21ca8019cd30";
		String test2 = "22ffdcab7e02005f|1293907/680770/16080|t=1389303219|et=730|cs=002213fd4891bd21ca8019cd30";
		Id id = new Id(test2);
		System.out.println("breakpoint");
	}

}
