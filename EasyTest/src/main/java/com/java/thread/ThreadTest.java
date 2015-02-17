package com.java.thread;

public class ThreadTest extends Thread{
	private int threadNo;   
  public ThreadTest(int threadNo) {   
      this.threadNo = threadNo;   
  }   
  public static void main(String[] args) throws Exception {   
      for (int i = 1; i < 5; i++) {   
          Thread t1= new ThreadTest(i);   
          t1.start();
          t1.join();
          //Thread.sleep(1);   
      }   
  }   
  public synchronized void run() {   
      for (int i = 1; i < 10; i++) {   
          System.out.println("No." + threadNo + ":" + i);   
      }   
  }   

}
