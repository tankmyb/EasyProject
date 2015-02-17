package com.java.thread;

class WaitNotify implements Runnable{

	@Override
	public synchronized void run() {
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("============1");
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("============2");
	}
	public synchronized void notify1(){
		this.notify();
	}
	public synchronized void notify2(){
		this.notify();
	}
}
public class WaitNotifyTest {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("===============start");
		WaitNotify wn = new WaitNotify();
		Thread t1 = new Thread(wn);
	   t1.start();
	   System.out.println("slepp 3000");
	   Thread.sleep(3000);
	   wn.notify1();
	   System.out.println("slepp 2000");
	   Thread.sleep(2000);
	   wn.notify1();
	}
   
	
}
