package com.java.base;

import java.util.HashMap;

public class SampleMemoryLeak {
	HashMap container = new HashMap();
	long number;

	SampleMemoryLeak() {
		number = 0;
	}

	public void remove(long index) {
		container.remove(index);
	}

	public int size() {
		return container.size();
	}

	public static void main(String[] args) {
		System.out.println("===2==");
		try {
			SampleMemoryLeak ml = new SampleMemoryLeak();
			for (int i = 0; i < 200; i++) {
				for (int j = 0; j < 700; j++) {
					ml.leakMemory();
					ml.number++;

				}
				Thread.currentThread().sleep(1500);
				System.out.println(ml.size());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void setReference(GeneralObject gc) {
		container.put(number, gc);
		container.remove(number);
		gc = null;
	}

	public void leakMemory() {
		try {
			GeneralObject gc = new GeneralObject();
			setReference(gc);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

class GeneralObject {
	int a;
	int b;
	String t1;
	String t2;

	GeneralObject() {
		a = 100;
		b = 400;
		t1 = "t1";
		t2 = "t2";
	}
}