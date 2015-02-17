package com.java.thread;

public class TextThread {
	/**
	  * @param args
	  */
	 public static void main(String[] args) 
	 {
	  // TODO 自动生成方法存根
	        TxtThread tt = new TxtThread();
	        new Thread(tt).start();
	        new Thread(tt).start();
	        new Thread(tt).start();
	        new Thread(tt).start();
	 }

	}
	class TxtThread implements Runnable
	{
	  private Integer num = 100;
	 String str = new String();
	 public void run()
	 {
	  while (num>0)
	  {
	   synchronized(str)//如果这里使用num是不能同步的，因为num是可变的。
	   {
	   if (num>0)
	   {
	    try
	    {
	     Thread.sleep(10);
	    }
	    catch(Exception e)
	    {
	     e.getMessage();
	    }
	    System.out.println(Thread.currentThread().getName()+ "this is "+ num--);
	   }
	   }
	  }
	 }


}
