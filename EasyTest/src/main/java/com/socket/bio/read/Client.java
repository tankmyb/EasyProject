package com.socket.bio.read;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread{
	Socket socket;
	BufferedReader in;
	PrintWriter out;

	public Client() {
		try {
			socket = new Socket("127.0.0.1", 10000);
			socket.setSoTimeout(1000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
		} catch (IOException e) {
		}
	}
  public void run() {
  	int i=0;
    while(true){
    	out.println("test");
    	out.flush();
    	
    	try {
			String line = in.readLine();
			System.out.println(line);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};
    	
    	
    }
  }
	public static void main(String[] args) {
		new Client().start();
	}

}
