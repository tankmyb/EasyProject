package com.socket.bio.cqs.v2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer2 {

	public MyServer2() {
		ServerSocket server;
		try {
			server = new ServerSocket(5680);
			System.out.println("server2:5680");
			while (true) {
				new ThreadHandler(server.accept()).start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static class ThreadHandler extends Thread {
		private Socket client;

		public ThreadHandler(Socket client) {
			this.client = client;
		}

		public void run() {
			BufferedReader in;
			System.out.println("connection:"
					+ client.getInetAddress().getHostAddress() + ":"
					+ client.getPort());
			try {
				in = new BufferedReader(new InputStreamReader(
						client.getInputStream(), "utf-8"));
				PrintWriter out = new PrintWriter(new BufferedWriter(
	        			new OutputStreamWriter(client.getOutputStream(), "utf-8")), true);
				while (true) {
					String str = in.readLine();
					System.out.println(str);
					out.println("5680 receive...." + str);
					out.flush();
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	public static void main(String[] args) throws IOException {
		new MyServer2();
	}
}
