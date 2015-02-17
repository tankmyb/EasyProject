package com.socket.bio.css.v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer2 {
	private static final MyServer2 server = new MyServer2();

	private MyServer2() {
		ServerSocket server;
		try {
			server = new ServerSocket(5680);
			System.out.println("server1:5680");
			Socket client = null;
			while (true) {
				client = server.accept();
				handler(client);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static MyServer2 getInstance() {
		return server;
	}

	public void handler(Socket client) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				client.getInputStream(), "GBK"));
		PrintWriter out = new PrintWriter(client.getOutputStream());
		while (true) {
			String str = in.readLine();
			System.out.println(str);
			if (str != null) {
				out.println("5680 receive...." + str);
				out.flush();

			} else {
				break;
			}
		}
		client.close();
	}

	public static void main(String[] args) throws IOException {

	}
}
