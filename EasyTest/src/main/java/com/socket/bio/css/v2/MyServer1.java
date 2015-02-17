package com.socket.bio.css.v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer1 {
	private static final MyServer1 server = new MyServer1();

	private MyServer1() {
		ServerSocket server;
		try {
			server = new ServerSocket(5679);
			System.out.println("server1:5679");
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

	public static MyServer1 getInstance() {
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
				out.println("5679 receive...." + str);
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
