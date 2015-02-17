package com.java.thread.pc;

public class Producer implements Runnable //生产者线程类
{
  SnycStack ss = null;
  public Producer(SnycStack ss) //因为生产者生产完，放入栈中，所以给构造方法传递 //一个栈的引用
  {
  this.ss = ss;
  }

  public void run()
  {
  for(int i=0;i<20;i++)
  {
  WoTou wt = new WoTou(i);
  ss.push(wt);
  System.out.println("生产了: "+wt);
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
