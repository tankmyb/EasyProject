package com.proxool.t1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client{
	Socket socket;
	BufferedReader in;
	PrintWriter out;

	public Client() {
		try {
			socket = new Socket("127.0.0.1", 10000);
			socket.setSoTimeout(1000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			for(int i=0;i<1000;i++){
				out.println("aaa:"+i);
				out.flush();
			}
		} catch (IOException e) {
		}
	}
	public static void main(String[] args) {
		new Client();
	}

}
