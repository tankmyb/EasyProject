package com.java.thread;

public class TestThread extends Thread {

	 

	  private int a = 0;

	  private int b = 0;

	 

	  public static void main(String[] args) {

	    TestThread test = new TestThread();

	    for (int i = 0; i < 10; i++) {

	      Thread thread = new Thread(test, "thread-" + i);

	      thread.start();

	    }

	  }

	 

	  public synchronized void doWrite() {

	    a++;

	    try {

	      sleep((int)(Math.random()*100));

	    }

	    catch (InterruptedException e) {

	    }

	    b++;

	    try {

	      sleep((int)(Math.random()*100));

	    }

	    catch (InterruptedException e) {

	    }
	    print();
	  }

	 

	  public void print() {

	    System.out.println("" + Thread.currentThread().getName() + ":a:" + a);

	    System.out.println("" + Thread.currentThread().getName() + ":b:" + b);

	  }

	 

	  public  void run() {

	    super.run();    //To change body of overridden methods use File | Settings | File Templates.

	    for (int i = 0; i < 10; i++) {

	      doWrite();

	     // print();

	    }

	  }

	 

	  public synchronized void start() {

	    super.start();    //To change body of overridden methods use File | Settings | File Templates.

	  }

}
