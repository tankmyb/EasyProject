package com.java.jvm;

/**
 * addShutdownHook是指，增加JVM停止时要做处理事件。当JVM停止时，就会把之前增加的这些HOOK逐个运行。
 * 运行这个例子可以简单体会到它的作用．
 * 
 * @author Administrator
 * 
 */
public class AddShutdownHookTest {
	static class Thread1 extends Thread {
		public void run() {
			int i = 0;
			while (i < 10) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException ex) {
				}
				System.out.println("~Thread 1~");
				i++;
			}
		}
	}

	static class Thread2 extends Thread {
		public void run() {
			int i = 0;
			while (i < 10) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
				}
				System.out.println("~Thread 2~");
				i++;
			}
		}
	}

	static class Thread3 extends Thread {
		public void run() {
			System.out.println("---end---");
		}
	}

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread3());
		Thread1 t1 = new Thread1();
		t1.start();
		Thread2 t2 = new Thread2();
		t2.start();
	}

}
