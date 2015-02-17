package com.java.thread.pc;

public class SnycStack {
	int index = 0;
  WoTou []arrwt = new WoTou[6];
  public synchronized void push(WoTou wt) //向栈里放
  {
  while(index == arrwt.length)
  try
  {
  	System.out.println("满了");
  this.wait();
  }catch(InterruptedException e)
  {
  e.printStackTrace();
  }
  arrwt[index++] = wt;
  this.notify();
  
  }
  public synchronized WoTou pop() //从栈里取
  {
  while(index==0)
  try
  {
  	System.out.println("空了");
  this.wait();
  }catch(InterruptedException e)
  {
  e.printStackTrace();
  }
  this.notify();
  index--;
  return arrwt[index];
  }

}
