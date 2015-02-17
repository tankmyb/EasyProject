package com.java.juc.lock.reentrantLock.testInterruptWithSyncAndLock;

public class SyncBuffer implements Buffer{
	private Object lock;   
  
  public SyncBuffer() {   
      lock = this;   
  }   
  
  public void write() {   
      synchronized (lock) {   
          long startTime = System.currentTimeMillis();   
          System.out.println("开始往这个buff写入数据…");   
          for (;;)// 模拟要处理很长时间   
          {   
              if (System.currentTimeMillis()   
                      - startTime > 10000)   
                  break;   
          }   
          System.out.println("终于写完了");   
      }   
  }   
  
  public void read() {   
      synchronized (lock) {   
          System.out.println("从这个buff读数据");   
      }   
  }   
}
