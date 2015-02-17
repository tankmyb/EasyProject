package com.java.thread.pc.lockCondition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SnycStack {
	int index = 0;
  WoTou []arrwt = new WoTou[6];
  Lock lock = new ReentrantLock();
  Condition notFull = lock.newCondition();
  Condition notEmpty = lock.newCondition();
  public void push(WoTou wt) //向栈里放
  {
  	lock.lock();
  	try
    {
  while(index == arrwt.length)
  {
  	System.out.println("满了");
  	notFull.await();//已经满了，所以notFull的条件不满足，所以挂起
  }
  arrwt[index++] = wt;
  notEmpty.signalAll();
    }catch(InterruptedException e)
    {
    e.printStackTrace();
    }finally{
    	lock.unlock();
    }
  }
  public synchronized WoTou pop() //从栈里取
  {
  	lock.lock();
  	try
    {
  while(index==0)
  {
  	System.out.println("空了");
  	notEmpty.await();
  }
  notFull.signalAll();
  index--;
    }catch(InterruptedException e)
    {
    e.printStackTrace();
    }finally{
    	lock.unlock();
    }
  return arrwt[index];
  }

}
