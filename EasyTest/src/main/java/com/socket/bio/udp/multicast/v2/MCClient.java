package com.socket.bio.udp.multicast.v2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MCClient {
	public static void main(String[] args) throws IOException {
		// Create a MulticastSocket bound to local port 10000. All
		// multicast packets from the server program are received
		// on that port.

		MulticastSocket s = new MulticastSocket(10000);

		// Obtain an InetAddress object that contains the multicast
		// group address 231.0.0.1. The InetAddress object is used by
		// DatagramPacket.

		InetAddress group = InetAddress.getByName("231.0.0.1");

		// Join the multicast group so that datagram packets can be
		// received.

		s.joinGroup(group);

		// Read several datagram packets from the server program.

		for (int i = 0; i < 100; i++) {
			// No line will exceed 256 bytes.

			byte[] buffer = new byte[256];

			// The DatagramPacket object needs no addressing
			// information because the socket contains the address.

			DatagramPacket dgp = new DatagramPacket(buffer, buffer.length);

			// Receive a datagram packet.

			s.receive(dgp);

			// Create a second byte array with a length that matches
			// the length of the sent data.

			byte[] buffer2 = new byte[dgp.getLength()];

			// Copy the sent data to the second byte array.

			System.arraycopy(dgp.getData(), 0, buffer2, 0, dgp.getLength());

			// Print the contents of the second byte array. (Try
			// printing the contents of buffer. You will soon see why
			// buffer2 is used.)

			System.out.println(new String(buffer2));
		}

		// Leave the multicast group.

		s.leaveGroup(group);

		// Close the socket.

		s.close();
	}

}
