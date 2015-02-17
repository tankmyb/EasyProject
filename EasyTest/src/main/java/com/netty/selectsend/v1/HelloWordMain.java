package com.netty.selectsend.v1;

public class HelloWordMain {
	public static void main(String[] args) throws InterruptedException {
		ClientThread r = new ClientThread();
		Thread t = new Thread(r);
		t.setName("client thread");
		t.start();
		Thread.sleep(1000);
        r.sendMsg("/127.0.0.1:61976,hello /127.0.0.1:61976");
	}
}
