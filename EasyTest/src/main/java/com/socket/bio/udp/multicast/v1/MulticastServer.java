package com.socket.bio.udp.multicast.v1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastServer {
	public static void main(String args[]) throws java.io.IOException
	{
		new MulticastServerThread().start();
		// 启动一个服务器线程
	}

	static class MulticastServerThread extends Thread
	{
		private static final long FIVE_SECOND = 5000; // 定义常量，5秒钟

		public void run() // 重写父类的线程主体

		{
			MulticastSocket s = null;
			try {
				s = new MulticastSocket();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while (true) {

				// 根据标志变量判断是否继续循环

				try {

					byte[] buf = new byte[256];

					// 创建缓冲区

					String dString = "Hello world!";

					// 否则调用成员函数从文件中读出字符串

					buf = dString.getBytes();

					// 把String转换成字节数组，以便传送send it

					InetAddress group = InetAddress.getByName("230.0.0.1");

					// 得到230.0.0.1的地址信息

					DatagramPacket packet = new DatagramPacket(buf, buf.length,
							group, 4446);

					// 根据缓冲区，广播地址，和端口号创建DatagramPacket对象

					s.send(packet); // 发送该Packet

					try {

						sleep((long) (Math.random() * FIVE_SECOND));

						// 随机等待一段时间，0～5秒之间

					} catch (InterruptedException e) {
					} // 异常处理

				} catch (IOException e) { // 异常处理

					e.printStackTrace(); // 打印错误栈

				}

			}

			// s.close( ); //关闭广播套接口

		}
	}
}
