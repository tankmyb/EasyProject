package com.socket.bio.siimple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	Socket socket;
	BufferedReader in;
	PrintWriter out;

	public Client() {
		try {
			socket = new Socket("127.0.0.1", 10000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader line = new BufferedReader(new InputStreamReader(System.in));

			out.println(line.readLine());
			line.close();
			out.close();
			in.close();
			socket.close();
			System.out.println("======================");
		} catch (IOException e) {
		}
	}

	public static void main(String[] args) {
		new Client();
	}

}
