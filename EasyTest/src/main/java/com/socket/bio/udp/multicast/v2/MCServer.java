package com.socket.bio.udp.multicast.v2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MCServer {
	public static void main(String[] args) throws IOException {
		System.out.println("Server starting...\n");

		// Create a MulticastSocket not bound to any port.

		MulticastSocket s = new MulticastSocket();

		// Because MulticastSocket subclasses DatagramSocket, it is
		// legal to replace MulticastSocket s = new MulticastSocket ();
		// with the following line.

		// DatagramSocket s = new DatagramSocket ();

		// Obtain an InetAddress object that contains the multicast
		// group address 231.0.0.1. The InetAddress object is used by
		// DatagramPacket.

		InetAddress group = InetAddress.getByName("231.0.0.1");

		// Create a DatagramPacket object that encapsulates a reference
		// to a byte array (later) and destination address
		// information. The destination address consists of the
		// multicast group address (as stored in the InetAddress object)
		// and port number 10000 -- the port to which multicast datagram
		// packets are sent. (Note: The dummy array is used to prevent a
		// NullPointerException object being thrown from the
		// DatagramPacket constructor.)

		byte[] dummy = new byte[0];

		DatagramPacket dgp = new DatagramPacket(dummy, 0, group, 10000);

		// Send 30000 Strings to the port.

		for (int i = 0; i < 30000; i++) {
			// Create an array of bytes from a String. The platform's
			// default character set is used to convert from Unicode
			// characters to bytes.

			byte[] buffer = ("Video line " + i).getBytes();

			// Establish the byte array as the datagram packet's
			// buffer.

			dgp.setData(buffer);

			// Establish the byte array's length as the length of the
			// datagram packet's buffer.

			dgp.setLength(buffer.length);

			// Send the datagram to all members of the multicast group
			// that listen on port 10000.

			s.send(dgp);
		}

		// Close the socket.

		s.close();
	}

}
