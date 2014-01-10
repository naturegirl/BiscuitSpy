package com.BiscuitSpy.cookies;

/** Define a single HTTP cookie
 * 
 * @author marcela
 *
 */
public class Cookie {
	
	private String name;
	private String value;
	private String host; // this should be the domain whose cookie this is; this value needs to be extracted from the Host field of the HTTP request
	// other fields that could be relevant
	
	public Cookie(String n, String v, String h){
		name = n;
		value = v;
		host = h;
	}
	
	public Cookie(String v){
		value = v;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}	

}
