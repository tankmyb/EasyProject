package com.java.thread.interrupt;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 很幸运，Java平台为这种情形提供了一项解决方案，即调用阻塞该线程的套接字的close()方法。在这种情形下，如果线程被I/O操作阻塞，
 * 该线程将接收到一个SocketException异常
 * ，这与使用interrupt()方法引起一个InterruptedException异常被抛出非常相似。
 * 
 * 唯一要说明的是，必须存在socket的引用（reference），只有这样close()方法才能被调用。这意味着socket对象必须被共享。Listing
 * E描述了这一情形。运行逻辑和以前的示例是相同的。
 * 
 * 
 * @author Administrator
 * 
 */
public class Example5 extends Thread {
	volatile boolean stop = false;
	volatile ServerSocket socket;

	public static void main(String args[]) throws Exception {
		Example5 thread = new Example5();
		System.out.println("Starting thread...");
		thread.start();
		Thread.sleep(3000);
		System.out.println("Asking thread to stop...");
		thread.stop = true;
		thread.socket.close();
		Thread.sleep(3000);
		System.out.println("Stopping application...");
		// System.exit( 0 );
	}

	public void run() {
		try {
			socket = new ServerSocket(7856);
		} catch (IOException e) {
			System.out.println("Could not create the socket...");
			return;
		}
		while (!stop) {
			System.out.println("Waiting for connection...");
			try {
				Socket sock = socket.accept();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("accept() failed or interrupted...");
			}
		}
		System.out.println("Thread exiting under request...");
	}

}
