package com.socket.bio.cqs.v2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Queue {
	private ServerSocket server;
	private static ConcurrentHashMap<String, Socket> socketMap = new ConcurrentHashMap<String, Socket>();

	public Queue() {
		try {
			server = new ServerSocket(5678);
			System.out.println("server:5678");
			while (true) {
				new ThreadHandler(server.accept()).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Queue();

	}

	static class ThreadHandler extends Thread {
		private Socket client;
		static Socket queueClientSocket = null;
		static PrintWriter queueClientPw;
		static BufferedReader queueClientBr;

		public ThreadHandler(Socket client) {
			this.client = client;
		}

		public void run() {
			BufferedReader in;
			String port = null;
			System.out.println("connection:"
					+ client.getInetAddress().getHostAddress() + ":"
					+ client.getPort());
			try {
				in = new BufferedReader(new InputStreamReader(
						client.getInputStream(), "UTF-8"));
				PrintWriter out = new PrintWriter(new BufferedWriter(
	        			new OutputStreamWriter(client.getOutputStream(), "utf-8")), true);
				while (true) {
					String str = in.readLine();
					if (str.equals("end")) {
						System.out.println("connection close");
						out.println("end");
						out.flush();
						break;
					}
					if (str.indexOf(":") == -1) {
						System.out.println("格式错误：" + str);
						out.println("格式错误：" + str);
						out.flush();
						continue;
					}
					port = str.split(":")[0];
					if (socketMap.containsKey(port)) {
						queueClientSocket = socketMap.get(port);

					} else {
						try {
							queueClientSocket = new Socket(
									InetAddress.getLocalHost(),
									Integer.parseInt(port));
						} catch (Exception e) {
							System.out.println("服务器断开连接：" + port);
							out.println("服务器断开连接：" + port);
							out.flush();
							continue;
						}
						System.out.println("creater conncetion"+queueClientSocket);
						socketMap.put(port, queueClientSocket);
					}
					try{
						queueClientSocket.sendUrgentData(0xFF);
					}catch (Exception e) {
						System.out.println("服务器断开连接2：" + port);
						out.println("服务器断开连接2：" + port);
						out.flush();
						socketMap.remove(port);
						continue;
					}
					

					str = str.split(":")[1];
					System.out.println(str);
					queueClientPw = new PrintWriter(new BufferedWriter(
		        			new OutputStreamWriter(queueClientSocket.getOutputStream(), "utf-8")), true);
					queueClientPw.println("queue:" + str);
					queueClientPw.flush();
					queueClientBr = new BufferedReader(new InputStreamReader(
							queueClientSocket.getInputStream(),"utf-8"));
					String server1Str = null;
					while (true) {
						server1Str = queueClientBr.readLine();
						if (server1Str != null) {
							break;
						}
					}
					out.println(server1Str);
					out.flush();
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}
}
