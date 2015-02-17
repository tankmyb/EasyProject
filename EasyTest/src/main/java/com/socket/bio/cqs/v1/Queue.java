package com.socket.bio.cqs.v1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Queue {
	public static void main(String[] args) throws IOException,
			InterruptedException {
		ServerSocket server = new ServerSocket(5678);
		System.out.println("server:5678");
		ConcurrentHashMap<String,Socket> socketMap = new ConcurrentHashMap<String, Socket>();
	    
		while (true) {
			Socket client = server.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					client.getInputStream(), "GBK"));
			PrintWriter out = new PrintWriter(client.getOutputStream());
			String port = null;
			while (true) {
				String str = in.readLine();
				port=str.split(":")[0];
				if(!socketMap.containsKey(port)){
					socketMap.put(port, new Socket(InetAddress.getLocalHost(), Integer.parseInt(port)));
				}
				Socket server1 = socketMap.get(port);
				str = str.split(":")[1];
				System.out.println(str);
				PrintWriter server1out = new PrintWriter(
						server1.getOutputStream());
				server1out.println("5678:" + str);
				server1out.flush();
				BufferedReader server1in = new BufferedReader(
						new InputStreamReader(server1.getInputStream()));
				String server1Str = null;
				while (true) {
					server1Str = server1in.readLine();
					if (server1Str != null) {
						break;
					}
				}

				out.println(server1Str);
				out.flush();
				if (str.equals("end")) {
					break;
				}
			}
			client.close();

		}
	}
}
