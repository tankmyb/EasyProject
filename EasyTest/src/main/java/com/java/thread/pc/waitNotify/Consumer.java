package com.java.thread.pc.waitNotify;

public class Consumer implements Runnable //消费者线程类
{
  SnycStack ss = null;
  private String name;
  public Consumer(SnycStack ss,String name)
  {
  this.ss = ss;
  this.name = name;
  }

  public void run()
  {
  for(int i=0;i<20;i++)
  {
  WoTou wt = ss.pop();
  System.out.println(Thread.currentThread().getName()+":消费了: "+wt);
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
