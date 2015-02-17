package com.java.thread.interrupt;

/**
 * 真正地中断一个线程
 * 
 * 中断线程最好的，最受推荐的方式是，使用共享变量（shared
 * variable）发出信号，告诉线程必须停止正在运行的任务。线程必须周期性的核查这一变量（尤其在冗余操作期间），然后有秩序地中止任务。Listing
 * B描述了这一方式。 虽然该方法要求一些编码，但并不难实现。同时，它给予线程机会进行必要的清理工作，这在任何一个多线程应用程序中都是绝对需要的。
 * 请确认将共享变量定义成volatile 类型或将对它的一切访问封入同步的块/方法（synchronized blocks/methods）中。
 * 
 * 
 * @author Administrator
 * 
 */
public class Example2 extends Thread {

	volatile boolean stop = false;

	public static void main(String args[]) throws Exception {

		Example2 thread = new Example2();

		System.out.println("Starting thread...");

		thread.start();

		Thread.sleep(3000);

		System.out.println("Asking thread to stop...");

		thread.stop = true;

		Thread.sleep(3000);

		System.out.println("Stopping application...");

		// System.exit( 0 );

	}

	public void run() {

		while (!stop) {

			System.out.println("Thread is running...");

			long time = System.currentTimeMillis();

			while ((System.currentTimeMillis() - time < 1000) && (!stop)) {

			}

		}

		System.out.println("Thread exiting under request...");

	}

}
