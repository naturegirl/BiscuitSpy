package com.BiscuitSpy.profiler;

import java.util.ArrayList;
import java.util.List;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Http.Request;


public class Capturer {
	
	public static int count = 0;

	/**
	 * Main startup method
	 * 
	 * @param args
	 *          ignored
	 */
	public static void capture() {
		List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs
		StringBuilder errbuf = new StringBuilder(); // For any error msgs

		/***************************************************************************
		 * First get a list of devices on this system
		 **************************************************************************/
		int r = Pcap.findAllDevs(alldevs, errbuf);
		if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
			System.err.printf("Can't read list of devices, error is %s", errbuf
			    .toString());
			return;
		}

		System.out.println("Network devices found:");

		int i = 0;
		for (PcapIf device : alldevs) {
			String description =
			    (device.getDescription() != null) ? device.getDescription()
			        : "No description available";
			System.out.printf("#%d: %s [%s]\n", i++, device.getName(), description);
		}

		PcapIf device = alldevs.get(1); // We know we have atleast 1 device
		System.out
		    .printf("\nChoosing '%s' on your behalf:\n",
		        (device.getDescription() != null) ? device.getDescription()
		            : device.getName());

		/***************************************************************************
		 * Second we open up the selected device
		 **************************************************************************/
		int snaplen = 64 * 1024;           // Capture all packets, no trucation
		int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
		int timeout = 10 * 1000;           // 10 seconds in millis
		Pcap pcap =
		    Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);

		if (pcap == null) {
			System.err.printf("Error while opening device for capture: "
			    + errbuf.toString());
			return;
		}

		/***************************************************************************
		 * Third we create a packet handler which will receive packets from the
		 * libpcap loop.
		 **************************************************************************/

		
		PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {

			final Tcp tcp = new Tcp(); 
			final Http http = new Http();
			
			public void nextPacket(PcapPacket packet, String user) {
				
				/* 
                 * A typical and common approach to getting headers from a packet is to 
                 * chain them as a condition for the if statement. If we need to work 
                 * with both tcp and http headers, for example, we place both of them on 
                 * the command line. 
                 */  
                if (packet.hasHeader(tcp) && packet.hasHeader(http)) {  
                    /* 
                     * Now we are guarranteed to have both tcp and http header peered. If 
                     * the packet only contained tcp segment even though tcp may have http 
                     * port number, it still won't show up here since headers appear right 
                     * at the beginning of http session. 
                     */  
  
                	if (http.hasField(Request.Cookie)) {
                		System.out.println("Found cookie packet");
                		count++;
                		sendSegment(http);
                		System.out.println(http.fieldValue(Request.Cookie));
                		System.out.println(http.fieldValue(Request.Referer));
                	}
                }  				
			}
		};

		/***************************************************************************
		 * Fourth we enter the loop and tell it to capture infinite packets (denoted by -1). The loop
		 * method does a mapping of pcap.datalink() DLT value to JProtocol ID, which
		 * is needed by JScanner. The scanner scans the packet buffer and decodes
		 * the headers. The mapping is done automatically, although a variation on
		 * the loop method exists that allows the programmer to sepecify exactly
		 * which protocol ID to use as the data link type for this pcap interface.
		 **************************************************************************/
		pcap.loop(-1, jpacketHandler, "jNetPcap rocks!");

		/*************************************************	**************************
		 * Last thing to do is close the pcap handle
		 **************************************************************************/
		pcap.close();
	}
	
	/** Sends the Http segment of a captured packet to the profiler
	 * 
	 * @param seg
	 */
	private static void sendSegment(Http seg){
		Profiler.recvHttpSegment(seg);
	}
}
