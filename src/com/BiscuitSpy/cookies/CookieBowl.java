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
	 * @param packet a captured HTTP packet
	 */
	public CookieBowl(Http packet){
		domain = packet.fieldValue(Request.Host);
		bowl = fillCookieBowl(packet.fieldValue(Request.Cookie));
		extracted = new Hashtable<String,Cookie>();
		System.out.println("created cookie bowl for a request to domain: "+domain);
	}

	private Hashtable<String,Cookie> fillCookieBowl(String cookies){
		if(cookies.equals("") || cookies == null){
			return new Hashtable<String,Cookie>();
		}
		
		Hashtable<String,Cookie> bowl = new Hashtable<String,Cookie>();		
		ArrayList<String> list = CookieUtils.tokenize(cookies,";");
		for(String cookieStr : list){
			String[] cookieComps = new String[2];
			cookieComps = cookieStr.split("=",2);
			System.out.println("Created new cookie: name = "+cookieComps[0]+" value = "+cookieComps[1]);
			Cookie cookie = new Cookie(cookieComps[0], cookieComps[1], domain);
			bowl.put(cookieComps[0], cookie);
		}
		
		return bowl;
	}
	
	/** Using this bowl, separate out the cookies used for profiling
	 * 
	 */
	public void extractProfileCookies(){
		for (String name : CookieUtils.spyCookies){
			Cookie cookie = (Cookie)bowl.get(name);
			if(cookie != null){
				extracted.put(name, cookie);
			}
		}
	}

	
} //ends CookieBowl
