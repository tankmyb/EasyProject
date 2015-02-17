package com.java.threadSafe.field;

import com.java.threadSafe.Vo;

/**
 * 用于测试线程类成员共用一个是否线程安全
 * 失败，因为写法有问题，新写法请看：TestThreadField2
 * @author Administrator
 *
 */

public class TestThreadField extends Thread{
   private Vo vo;
   public TestThreadField(Vo vo){
	   this.vo = vo;
   }
   public void run(){
	   vo.setName(Thread.currentThread().getName());
	   System.out.println("name1:"+vo.getName());
	   try {
		Thread.sleep(100L);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   System.out.println("name2:"+vo.getName());
   }
   public static void main(String[] args) {
	for(int i=0;i<100;i++){
		Vo vo = new Vo();
		new TestThreadField(new Vo()).start();
	}
}
}
