package com.java.thread.interrupt;

/**
 * 一些轻率的家伙可能被另一种方法Thread.interrupt所迷惑。尽管，其名称似乎在暗示着什么，然而，这种方法并不会中断一个正在运行的线程（
 * 待会将进一步说明），正如Listing
 * A中描述的那样。它创建了一个线程，并且试图使用Thread.interrupt方法停止该线程。Thread.sleep
 * ()方法的调用，为线程的初始化和中止提供了充裕的时间。线程本身并不参与任何有用的操作。
 * 甚至，在Thread.interrupt()被调用后，线程仍然继续运行
 * 
 * @author Administrator
 * 
 */
public class Example1 extends Thread {
	boolean stop = false;

	public static void main(String args[]) throws Exception {
		Example1 thread = new Example1();
		System.out.println("Starting thread...");
		thread.start();
		Thread.sleep(3000);
		System.out.println("Interrupting thread...");
		thread.interrupt();
		Thread.sleep(3000);
		System.out.println("Stopping application...");
		// System.exit(0);
	}

	public void run() {
		while (!stop) {
			System.out.println("Thread is running...");
			long time = System.currentTimeMillis();
			while ((System.currentTimeMillis() - time < 1000)) {
			}
		}
		System.out.println("Thread exiting under request...");
	}
}
