package com.java.threadSafe.field;

import com.java.threadSafe.Tools;
import com.java.threadSafe.Vo;

/**
 * 用于测试线程类成员共用一个是否线程安全
 * @author Administrator
 *
 */

public class TestThreadField3 extends Thread{
   private Vo vo;
   private String threadName;
   public TestThreadField3(Vo vo,String threadName){
	   this.vo = vo;
	   this.threadName = threadName;
   }
   public void run(){
	   vo.setName(Thread.currentThread().getName());
  	 Tools.update(vo);
	   System.out.println("name1:"+vo.getName());
	   try {
		Thread.sleep(1000L);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	   if(!vo.getName().equals(Thread.currentThread().getName())){
		   System.err.println("not equals");
	   }
	   System.out.println("name2:"+vo.getName());
   }
   
   public static void main(String[] args) {
  	 Vo vo = new Vo();
	for(int i=0;i<100;i++){
		
		new TestThreadField3(vo,"name"+i).start();
	}
}
}
