package com.socket.bio.simple;

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
			socket = new Socket("127.0.0.1", 9001);
			socket.setSoTimeout(10000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
		} catch (IOException e) {
		}
	}
  public void run() {
  	int i=0;
    while(true){
    	out.println("aaa:"+i);
    	String str = null;
			try {
				str = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	System.out.println(str);
    	i++;
    	if(i==100){
    		break;
    	}
    }
  }
	public static void main(String[] args) {
		new Client().start();
	}

}
