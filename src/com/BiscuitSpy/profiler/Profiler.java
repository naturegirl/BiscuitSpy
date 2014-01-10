package com.BiscuitSpy.profiler;

import java.util.concurrent.BlockingQueue;

import org.jnetpcap.protocol.tcpip.Http;


public class Profiler {
	
	private static BlockingQueue<Http> pendingSegments; //holds all the segments which are pending to be examined
	private static final Object queueLock = new Object(); //needed to synchronize access to the pending segments queue
	
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

}
