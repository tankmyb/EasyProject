package com.java.thread.interrupt;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 中断I/O操作
 * 然而，如果线程在I/O操作进行时被阻塞，又会如何？I/O操作可以阻塞线程一段相当长的时间，特别是牵扯到网络应用时。例如，服务器可能需要等待一个请求
 * （request），又或者，一个网络应用程序可能要等待远端主机的响应。
 * 
 * 如果你正使用通道（channels）（这是在Java 1.4中引入的新的I/O
 * API），那么被阻塞的线程将收到一个ClosedByInterruptException异常
 * 。如果情况是这样，其代码的逻辑和第三个例子中的是一样的，只是异常不同而已。
 * 
 * 但是，你可能正使用Java1.0之前就存在的传统的I/O，而且要求更多的工作。既然这样，Thread.interrupt()将不起作用，
 * 因为线程将不会退出被阻塞状态。Listing D描述了这一行为。尽管interrupt()被调用，线程也不会退出被阻塞状态
 * 
 * 
 * @author Administrator
 * 
 */
public class Example4 extends Thread {

	public static void main(String args[]) throws Exception {

		Example4 thread = new Example4();

		System.out.println("Starting thread...");

		thread.start();

		Thread.sleep(3000);

		System.out.println("Interrupting thread...");

		thread.interrupt();

		Thread.sleep(3000);

		System.out.println("Stopping application...");

		// System.exit( 0 );

	}

	public void run() {

		ServerSocket socket;

		try {

			socket = new ServerSocket(7856);

		} catch (IOException e) {

			System.out.println("Could not create the socket...");

			return;

		}

		while (true) {

			System.out.println("Waiting for connection...");

			try {

				Socket sock = socket.accept();

			} catch (IOException e) {

				System.out.println("accept() failed or interrupted...");

			}

		}

	}

}
