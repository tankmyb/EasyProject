package com.java.threadLocal;

import java.util.Random;

public class BaseThread implements Runnable{

	private Student s;
	public BaseThread(Student s){
		this.s = s;
	}
	@Override
	public void run() {
		Random random = new Random();
        int no = random.nextInt(100);
        s.setName("name"+no);
        System.out.println("thread " + Thread.currentThread().getName() + " set student name to:" + s.getName());

		
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("thread " +Thread.currentThread().getName()+",student name:"+s.getName());
	}
    public static void main(String[] args) {
    	Student s = new Student();
		BaseThread bt = new BaseThread(s);
		new Thread(bt).start();
		new Thread(bt).start();
	}
}
