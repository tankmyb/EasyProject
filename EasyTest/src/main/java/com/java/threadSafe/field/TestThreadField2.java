package com.java.threadSafe.field;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.java.threadSafe.Vo;

/**
 * 用于测试线程类成员共用一个是否线程安全
 * 失败，因为写法有问题，新写法请看：TestThreadField2
 * @author Administrator
 *
 */

public class TestThreadField2 implements Callable<Vo>{
   private Vo vo;
   private String threadName;
   public TestThreadField2(Vo vo,String name){
	   this.vo = vo;
	   this.threadName = name;
   }
   public Vo call(){
	   vo.setName(threadName);
	  System.out.println(Thread.currentThread().getName());
	   try {
		Thread.sleep(100L);
		System.out.println("------"+Thread.currentThread().getName());
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return vo;
   }
   public static void main(String[] args) throws InterruptedException, ExecutionException {
	   ExecutorService es = Executors.newFixedThreadPool(100);
	for(int i=0;i<100;i++){
		Vo vo = new Vo();
		String name = "name"+i;
		es.submit(new TestThreadField2(vo,name));
		//Future<Vo> future = es.submit(new TestThreadField2(vo,name));
		//System.out.println(name+"        "+future.get().getName());
	}
}
}
