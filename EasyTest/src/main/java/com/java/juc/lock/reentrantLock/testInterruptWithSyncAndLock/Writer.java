package com.java.juc.lock.reentrantLock.testInterruptWithSyncAndLock;

public class Writer extends Thread {   
  
  private Buffer buff;   
  
  public Writer(Buffer buff) {   
      this.buff = buff;   
  }   
  
  @Override   
  public void run() {   
      buff.write();   
  }   
  

}
