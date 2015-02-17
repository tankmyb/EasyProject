package com.socket.bio.mult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class Handler extends Thread{
  private Socket client;
  public Handler(Socket client){
  	this.client = client;
  }
	public void run() {
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream(),"GBK"));
			PrintWriter out=new PrintWriter(client.getOutputStream());
			while(true){
			String str=in.readLine();          //// 读取从client传来的数据信息
			System.out.println(str);           //服务器控制台输出数据信息
			out.println("has receive...."+str);  //服务器向客户端发送信息:has receive....
			out.flush();
			if(str.equals("end"))    
				break;    
				
			}
	   client.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
