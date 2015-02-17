package com.socket.bio.sendUrgentData;

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
    	try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				socket.sendUrgentData(0xff);
				out.flush();
				System.out.println("===============");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	if(i==100){
    		break;
    	}
    	i++;
    	
    }
  }
	public static void main(String[] args) {
		new Client().start();
	}

}
