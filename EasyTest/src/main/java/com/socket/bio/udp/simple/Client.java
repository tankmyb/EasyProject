package com.socket.bio.udp.simple;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
public static void main(String[] args) throws Exception{
	InetAddress target = InetAddress.getLocalHost();

	//得到目标机器的地址实例

	DatagramSocket ds = new DatagramSocket(9999);

	//从9999端口发送数据报

	for(int i=0;i<100000;i++){
		String hello = "Hello, I am come in!";

		//要发送的数据

		byte[] buf = hello.getBytes();

		//将数据转换成Byte类型

		DatagramPacket op = new DatagramPacket(buf, buf.length, target, 12345);

		//将BUF缓冲区中的数据打包

		ds.send(op);
		Thread.sleep(2L);
	}
	

	//发送数据

	ds.close();

	//关闭连接
}
}
