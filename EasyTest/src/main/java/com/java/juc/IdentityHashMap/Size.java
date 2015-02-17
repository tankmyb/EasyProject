package com.java.juc.IdentityHashMap;

import java.util.IdentityHashMap;

public class Size {

	public static void main(String[] args) throws InterruptedException {
		long start= System.currentTimeMillis();
		String s="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		IdentityHashMap<String, String> map = new IdentityHashMap<String, String>();
		for(int i=0;i<10000000;i++){
			map.put(new String(s+""), s+"");
			//System.out.println("======="+i);
		}
		System.out.println("==========="+map.size()+"==="+(System.currentTimeMillis()-start));
		Thread.sleep(1000000);
	}
}
