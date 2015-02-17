package com.socket.bio.sendUrgentData;

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
		new Thread(){
			public void run(){
				try {
					ss = new ServerSocket(10000);
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
						out.println("you input is :" + line);
						Thread.sleep(2000L);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					ss.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		
	}

	public static void main(String[] args) throws Exception{
		new Server();
	}

}
