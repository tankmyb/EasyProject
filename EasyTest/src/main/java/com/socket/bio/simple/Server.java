package com.socket.bio.simple;

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
			ss = new ServerSocket(9001);
      System.out.println("start:");
      boolean flag = false;
      socket = ss.accept();
      int index = 0;
			while (!flag) {
				
				System.out.println(socket.getInputStream()+"============");
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);

				String line = in.readLine();
				System.out.println(line+"================="+out);
				if(index ==10){
					Thread.sleep(2000L);
				}
				out.println("you input is :" + line);
				index++;
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
