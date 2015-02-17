package com.socket.bio.cqs.v2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	Socket server;
	BufferedReader in;
	PrintWriter out;

	private void getConnection() {
		try {
			server = new Socket(InetAddress.getLocalHost(), 5678);
			server.setSoTimeout(2000);
			in = new BufferedReader(new InputStreamReader(
					server.getInputStream(), "UTF-8"));;
			out = new PrintWriter(new BufferedWriter(
        			new OutputStreamWriter(server.getOutputStream(), "utf-8")), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connection() {
		//new Thread() {
			//public void run() {
				try {
					getConnection();
					BufferedReader wt = new BufferedReader(
							new InputStreamReader(System.in));

					while (true) {
						String str = wt.readLine();
						out.println(str);
						//out.flush();
						if (str.equals("end")) {
							break;
						}
						try {
							System.out.println(in.readLine());
						} catch (Exception e) {
							System.out.println("错误");
							getConnection();
						}
					}
					server.close();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			//}
		//}.start();
	}

	public static void main(String[] args) throws Exception {
		new Client().connection();
	}

}
