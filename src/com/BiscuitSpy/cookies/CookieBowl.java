package com.BiscuitSpy.cookies;

import java.util.ArrayList;
import java.util.Hashtable;

import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Http.Request;

/** Defines collection of cookies for a given request
 * 
 * @author marcela
 *
 */
public class CookieBowl {

	// useful fields from packet
	String requestSrcIp; // in the form of IP address
	String requestDestIp; // in the form of IP address
	String domain;
	
	// bowl characteristics
	Hashtable<String,Cookie> bowl;
	Hashtable<String, Cookie> extracted;
	
	/** Creates a cookie bowl based on an HTTP packet passed in from the packet capture.
	 * This assumes that the packet has been screened for an HTTP header and Cookie field.
	 * 
	 * @param packet a capture HTTP packet
	 */
	public CookieBowl(Http packet){
		// extract source IP addr, dest IP addr and domain from packet
		bowl = fillCookieBowl(packet.fieldValue(Request.Cookie));
		extracted = new Hashtable<String,Cookie>();
	}

	private Hashtable<String,Cookie> fillCookieBowl(String cookies){
		if(cookies.equals("") || cookies == null){
			return new Hashtable<String,Cookie>();
		}
		
		Hashtable<String,Cookie> bowl = new Hashtable<String,Cookie>();		
		ArrayList<String> list = CookieUtils.tokenize(cookies,";");
		for(String cookieStr : list){
			ArrayList<String> pair = CookieUtils.tokenize(cookieStr, "=");
			// TODO extract cookie info from request, will have to pass in request or simply store entire packet associated with bowl
		}
		return null;
	}
	
	/** Using this bowl, separate out the cookies used for profiling
	 * 
	 */
	public void extractProfileCookies(){
		
	}

	
} //ends CookieBowl
