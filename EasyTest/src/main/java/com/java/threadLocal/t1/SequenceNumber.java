package com.java.threadLocal.t1;

public class SequenceNumber {
	private  ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {

		public Integer initialValue() {

			return 0;

		}

	};

	public int getNextNum() {

		seqNum.set(seqNum.get() + 1);

		return seqNum.get();

	}

	public static void main(String[] args)

	{

		SequenceNumber sn = new SequenceNumber();

		TestClient t1 = new TestClient(sn);

		TestClient t2 = new TestClient(sn);

		TestClient t3 = new TestClient(sn);

		t1.start();

		t2.start();

		t3.start();

	}
}
