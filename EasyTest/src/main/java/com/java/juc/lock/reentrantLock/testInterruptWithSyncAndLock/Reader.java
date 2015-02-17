package com.java.juc.lock.reentrantLock.testInterruptWithSyncAndLock;

public class Reader extends Thread {   
  
  private Buffer buff;   
  
  public Reader(Buffer buff) {   
      this.buff = buff;   
  }   
  
  @Override   
  public void run() {   
  
      try {
				buff.read();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//这里估计会一直阻塞   
  
      System.out.println("读结束");   
  
  }   

}
