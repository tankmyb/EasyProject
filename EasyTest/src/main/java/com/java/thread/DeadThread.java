package com.java.thread;

public class DeadThread {

	private static Integer i1 = new Integer(1);
	private static Integer i2 = new Integer(1);
	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (i1) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					synchronized (i2) {
						System.out.println("==============i2");
					}
				}
				
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (i2) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					synchronized (i1) {
						System.out.println("==============i1");
					}
				}
				
			}
		}).start();
	}
}
