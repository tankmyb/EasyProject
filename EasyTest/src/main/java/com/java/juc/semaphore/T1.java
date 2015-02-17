package com.java.juc.semaphore;

import java.util.concurrent.Semaphore;

public class T1 {

	public static void main(String[] args) throws InterruptedException {
		final Semaphore pass = new Semaphore(1);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("====================start handler");
				try {
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				pass.release();
				System.out.println("====================handler finished");
			}
		}).start();
		pass.acquire();
		System.out.println("====================end");
	}
}
