package com.java.threadLocal;

import java.util.Random;

public class BaseThread1 implements Runnable{

	private String s;
	public BaseThread1(String s){
		this.s = s;
	}
	@Override
	public void run() {
		System.out.println(s.getClass().hashCode()+"==========");
		Random random = new Random();
        int no = random.nextInt(100);
        s="name"+no;
        System.out.println("thread " + Thread.currentThread().getName() + " set student name to:" + s);

		
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("thread " +Thread.currentThread().getName()+",student name:"+s);
	}
    public static void main(String[] args) {
    	String a = "aaa";
		BaseThread1 bt = new BaseThread1(a);
		new Thread(bt).start();
		new Thread(bt).start();
	}
}