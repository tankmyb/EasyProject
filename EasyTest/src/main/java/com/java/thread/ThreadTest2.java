package com.java.thread;

public class ThreadTest2  extends Thread {   
  private int threadNo;   
  private String lock;   
  private static String aa = "aa";
  public ThreadTest2(int threadNo, String lock) {   
      this.threadNo = threadNo;   
      this.lock = lock;   
  }   
  public static void main(String[] args) throws Exception {   
      String lock = new String("lock");   //synchronized 不能用可以改变的属性
      for (int i = 1; i < 5; i++) {   
          new ThreadTest2(i, lock).start();   
          //Thread.sleep(100);   
      }   
  }   
  public void run() {   
      synchronized (aa) {  //使用lock，类class也ok 
          for (int i = 1; i < 10; i++) {   
              System.out.println("No." + threadNo + ":" + i);   
          }   
      }   
  }   


}
