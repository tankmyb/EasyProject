package com.cache.map;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {

	static void t1(int size){
		for(int i=0;i<size;i++){
			Calendar calendar = Calendar.getInstance();
			calendar.get(Calendar.MINUTE);
		}
	}
	static void t2(int size){
		AtomicInteger a = new AtomicInteger();
		for(int i=0;i<size;i++){
			a.incrementAndGet();
		}
	}
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		t2(10000000);
		System.out.println(System.currentTimeMillis()-start);
	}
}
