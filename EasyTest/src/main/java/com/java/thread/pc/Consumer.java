package com.java.thread.pc;

public class Consumer implements Runnable //消费者线程类
{
  SnycStack ss = null;

  public Consumer(SnycStack ss)
  {
  this.ss = ss;
  }

  public void run()
  {
  for(int i=0;i<20;i++)
  {
  WoTou wt = ss.pop();
  System.out.println("消费了: "+wt);
  try
  {
  Thread.sleep(1000);
  }catch(InterruptedException e)
  {
  e.printStackTrace();
  }
  }
  }


}
