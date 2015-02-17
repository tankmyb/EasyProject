package com.netty.selectsend.v1;

public class ConnectMain {
	public static void main(String[] args) throws InterruptedException {
		ClientThread r = new ClientThread();
		Thread t = new Thread(r);
		t.setName("client thread");
		t.start();
		Thread.sleep(1000);
	}
}
