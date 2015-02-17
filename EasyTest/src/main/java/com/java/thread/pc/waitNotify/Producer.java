package com.java.thread.pc.waitNotify;

public class Producer implements Runnable //生产者线程类
{
  SnycStack ss = null;
  private String name;
  public Producer(SnycStack ss,String name) //因为生产者生产完，放入栈中，所以给构造方法传递 //一个栈的引用
  {
  this.ss = ss;
  this.name = name;
  }

  public void run()
  {
  for(int i=0;i<20;i++)
  {
  WoTou wt = new WoTou(i);
  ss.push(wt);
  System.out.println(Thread.currentThread().getName()+":生产了: "+wt);
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
