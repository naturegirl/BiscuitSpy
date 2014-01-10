package com.BiscuitSpy.profiler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.ArrayList;

import com.BiscuitSpy.cookies.*;
import com.BiscuitSpy.cookies.definitions.*;

import org.jnetpcap.protocol.tcpip.Http;


public class Profiler {
	
	private static ArrayBlockingQueue<Http> pendingSegments = new ArrayBlockingQueue<Http>(10); //holds all the segments which are pending to be examined
	private static final Object queueLock = new Object(); //needed to synchronize access to the pending segments queue
	
	private static ArrayList<Cookie> profile = new ArrayList<Cookie>();
	
	/** Used for passing Http packet segments from the capturer to the profiler
	 * 
	 * @param seg the current captured segment
	 */
	public static void recvHttpSegment(Http seg){
		synchronized(queueLock){
			addHttpSegment(seg);
		}
	}
	
	/** Adds the given Http segment of a packet if valid
	 * 
	 * @param seg the Http segment to add to the pending queue
	 */
	private static void addHttpSegment(Http seg){
		try {
			System.out.println("Added packet");                                 
			pendingSegments.put(seg);
		} catch (InterruptedException e) {
			System.out.println("Profiler: Some error occurred while trying to read more segments.");
			e.printStackTrace();
		}
	}
	
	/** Gets the next Http segment pending to be profiled
	 * 
	 * @return the next pending segment in the queue
	 */
	public static Http getNextSegment(){
		synchronized(queueLock){
			try {
				return pendingSegments.take();
			} catch (InterruptedException e) {
				System.out.println("Profiler: Some error occurred while trying to read more segments.");
				e.printStackTrace();
				return null;
			}
		}
	}

	
	public static int getNumPending(){
		synchronized(queueLock){
			return pendingSegments.size();
		}
	}
	
	public static void makeProfile(Http seg){
		CookieBowl bowl = new CookieBowl(seg);
		
		bowl.extractProfileCookies();
		
		for(String name : bowl.getExtracted().keySet()){
			Cookie cookie = bowl.getExtracted().get(name);
			
			Cookie profCookie;
			if(name == "__utma"){
				profCookie = new Utma(cookie.getValue());
			}
			else if(name == "PREF"){
				profCookie = new Pref(cookie.getValue());
			}
			else if(name == "Id"){
				profCookie = new Id(cookie.getValue());
			}
			else if(name == "wpc_wpc"){
				profCookie = new Wordpress(cookie.getValue());
			}
		
		}
	}
	
	
}
