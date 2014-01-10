package com.BiscuitSpy.testing;

import com.BiscuitSpy.cookies.*;
import com.BiscuitSpy.profiler.*;

public class CookieTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		System.out.println("Starting to capture packets");
		
		Capturer.capture();
		
		if(Profiler.getNumPending() == 10)
			System.out.println("Captured 10 packets");
		
	}
	
}
