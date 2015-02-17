package com.socket.bio.read.v1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client extends Thread{
	Socket socket;
	BufferedReader in;
	PrintWriter out;

	public Client() {
		try {
			socket = new Socket();
			socket.setSoTimeout(1000);
			socket.connect(new InetSocketAddress("localhost", 10000));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
		} catch (IOException e) {
		}
	}
  public void run() {
  	int i=0;
    while(true){
    	try {
    	if(in.ready()){
    		String line = in.readLine();
			System.out.println(line);
    	}else {
    		out.println("test");
        	out.flush();
    	}
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
