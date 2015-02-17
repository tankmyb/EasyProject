package com.java.thread.base;

import org.junit.Test;

public class TestFor {

	@Test
	public void test1(){
		long start = System.currentTimeMillis();
		for(int i=0;i<100000;i++){
			Integer[] array = new Integer[10000];
		}
		long end = System.currentTimeMillis();
		System.out.println("放在for里："+(end-start));
		
		long start1 = System.currentTimeMillis();
		Integer[] array = null;
		for(int i=0;i<100000;i++){
		  array = new Integer[10000];
		}
		long end1 = System.currentTimeMillis();
		System.out.println("放在for外："+(end1-start1));
	}
}
