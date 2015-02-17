package com.java.thread;


public class Second {
  static class SecondThread implements Runnable{
 	 static Integer index=0;
 	
 		@Override
 		public  void run() {
 			Long begin =System.currentTimeMillis();
 			//synchronized (index) {//这里使用this会有问题
 				for(;index<100;index++){
   			 System.out.println(Thread.currentThread().getName() +"============"+index);
   		 //}
 				

			}
 			Long end =System.currentTimeMillis();
				System.out.println(Thread.currentThread().getName()+":一共运行了" + (end - begin) + "毫秒");

 			
 			
 		}
  }
  public static void main(String[] args) throws InterruptedException {
 	 
 	 Thread f1 = new Thread(new SecondThread());
 	 Thread f2 = new Thread(new SecondThread());
 	 f1.start();
 	//Thread.sleep(10);//使用this需要睡一下
 	 f2.start();
 	
	}

}
