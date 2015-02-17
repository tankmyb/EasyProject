package com.java.threadLocal.t1;

public class TestClient extends Thread

{

	private SequenceNumber sn;

	public TestClient(SequenceNumber sn) {

		this.sn = sn;

	}

	public void run()

	{

		for (int i = 0; i < 3; i++) {

			System.out.println("thread[" + Thread.currentThread().getName() +

			"] sn[" + sn.getNextNum() + "]");

		}

	}

}
