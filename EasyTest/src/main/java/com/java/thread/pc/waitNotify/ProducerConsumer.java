package com.java.thread.pc.waitNotify;

public class ProducerConsumer {
	public static void main(String []args)
  {
  SnycStack ss = new SnycStack();
  Producer p = new Producer(ss,"p1");
  Consumer c = new Consumer(ss,"c1");
  Thread pro1 = new Thread(p);
  Thread con1 = new Thread(c);
  
  Thread pro2 = new Thread(p);
  Thread con2 = new Thread(c);
  pro1.start();
  con1.start();
  
  pro2.start();
  con2.start();
  }

}
