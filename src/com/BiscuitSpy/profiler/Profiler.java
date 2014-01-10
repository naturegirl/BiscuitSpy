package com.BiscuitSpy.profiler;

import java.util.concurrent.BlockingQueue;

import org.jnetpcap.protocol.tcpip.Http;


public class Profiler {
	
	private static BlockingQueue<Http> pendingSegments; //holds all the segments which are pending to be examined

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/** Used for passing Http packet segments from the capturer to the profiler
	 * 
	 * @param seg the current captured segment
	 */
	public static void recvHttpSegment(Http seg){
		addHttpSegment(seg);
	}
	
	private static void addHttpSegment(Http seg){
		try {
			pendingSegments.put(seg);
		} catch (InterruptedException e) {
			System.out.println("Profiler: Some error occurred while trying to read more segments.");
			e.printStackTrace();
		}
	}

}
