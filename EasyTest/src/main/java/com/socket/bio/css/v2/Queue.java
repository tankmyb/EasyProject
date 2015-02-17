package com.socket.bio.css.v2;

import java.io.IOException;
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
			new ThreadHandler(server.accept(),socketMap).start();
		}
	}
}
