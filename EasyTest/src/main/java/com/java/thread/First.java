package com.java.thread;

public class First {
	static Integer i=0;
   static class FirstThread extends Thread{
  	 public void run(){
  		 for( i=0;i<100;i++){
  			 System.out.println(this.getName()+"============"+i);
  		 }
  	 }
   }
   static class SecondThread implements Runnable{
  	 private Integer index =0;
  	 public SecondThread(){
  	 }
  	 public SecondThread(Integer i){
  		 index = i;
  	 }
  		@Override
  		public void run() {
  			for(;index<10;index++){
   			 System.out.println(Thread.currentThread().getName() +"============"+index);
   			/* try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
   		 }
  			
  		}
   }
   public static void main(String[] args) throws InterruptedException {
  	 /*FirstThread f1 = new FirstThread();
  	 FirstThread f2 = new FirstThread();
  	 f1.start();
  	 f2.start();*/
  	 
	  /*SecondThread t = new SecondThread();
  	 Thread f1 = new Thread(t);
  	 Thread f2 = new Thread(t);
  	 f1.start();
  	 //Thread.sleep(20L);
  	 f2.start();*/
	   
	   //如果每个线程都创建一个Runnable，那么里面的变量是独立
	  	 Thread f1 = new Thread(new SecondThread());
	  	 Thread f2 = new Thread(new SecondThread());
	  	 f1.start();
	  	 //Thread.sleep(20L);
	  	 f2.start();
	}


}
