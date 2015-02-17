package com.java.thread.pc;

public class ProducerConsumer {
	public static void main(String []args)
  {
  SnycStack ss = new SnycStack();
  Producer p = new Producer(ss);
  Consumer c = new Consumer(ss);
  Thread pro = new Thread(p);
  Thread con = new Thread(c);
  pro.start();
  con.start();
  }

}
