package com.BiscuitSpy.cookies.definitions;

import java.util.StringTokenizer;

import com.BiscuitSpy.cookies.CookieUtils;

public class Wordpress {
	
	// cookie name: wpc_wpc
	// wordpress wpc cookie structure:
	// has multiple fields divided by '&' where each field has structure: key=value
	// account=xxx&avatar=xxx&email=xxx&link=xxx&name=xxx&uid=xxx&access_token=xxx
	// see main() for example
	
	final static String DELIM = "&";
	final static int FIELD_COUNT = 7;
	
	String email, accountname, username;

	public Wordpress(String wpc_string) {
		if (wpc_string == null || wpc_string == "") {
			System.out.println("Error in Wordpress(): wpc_string is empty");
			return;
		}
		StringTokenizer st = new StringTokenizer(wpc_string, DELIM);
		if (st.countTokens() != FIELD_COUNT) {
			System.out.println("Error in Wordpress(): wordpress cookie tokens count incorrect");
			return;
		}
		accountname = CookieUtils.getFieldValue(st.nextToken());
		st.nextToken();	// skip avatar
		email = CookieUtils.getFieldValue(st.nextToken());
		st.nextToken();	// skip link
		username = CookieUtils.getFieldValue(st.nextToken());
	}
	
	public String getAccountName() {
		return accountname;
	}
	
	// username might be different from accountname
	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String wpc_string = "account=xuezhongwen&avatar=https%3A%2F%2F1.gravatar.com%2Favatar%2F420824352fe495c1b3b1dd0bc157def4%3Fs%3D25%26amp%3Bd%3Dhttps%253A%252F%252Fs2.wp.com%252Fwp-content%252Fmu-plugins%252Fhighlander-comments%252Fimages%252Fwplogo.png&email=nature_girl473%40yahoo.de&link=http%3A%2F%2Fgravatar.com%2Fxuezhongwen&name=naturegirl&uid=1757819&access_token=1d31013fd9a4613cfb7975c98553c4f762f32750";
		Wordpress wp = new Wordpress(wpc_string);
		System.out.println("breakpoint");
	}

}
