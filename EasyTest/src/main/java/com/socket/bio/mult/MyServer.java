package com.socket.bio.mult;

import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	public static void main(String[] args) throws Exception{
		ServerSocket server=new ServerSocket(5678);  //在端口5678上注册服务
		while(true){
			Socket client=server.accept();       // 监听窗口,等待连接
			new Handler(client).start();
		}
		
		

	}
}
