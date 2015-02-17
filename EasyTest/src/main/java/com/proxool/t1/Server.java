package com.proxool.t1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket ss;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public Server() throws IOException {
		try {
			ss = new ServerSocket(10000);
      System.out.println("start:");
      socket = ss.accept();
			while (true) {
				System.out.println(socket.getInputStream()+"============");
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);

				String line = in.readLine();
				out.println("you input is :" + line);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		ss.close();
	}

	public static void main(String[] args) throws Exception{
		new Server();
	}

}
