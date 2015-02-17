package com.socket.bio.siimple;

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
      boolean flag = false;
			while (!flag) {
				socket = ss.accept();
				System.out.println(socket.getInputStream()+"============");
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);

				String line = in.readLine();
				if(line.length()>0){
					flag = true;
				}
				System.out.println(line+"================="+out);
				out.println("you input is :" + line);
				out.close();
				in.close();
				socket.close();
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
