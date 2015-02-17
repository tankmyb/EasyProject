package com.java.juc.IdentityHashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapSize {

	public static void main(String[] args) throws InterruptedException {
        Thread.sleep(10000);
		long start = System.currentTimeMillis();
		/*String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < 1000000; i++) {
			map.put(new String(s + ""+i),new String(s + ""+i));
			// System.out.println("======="+i);
		}*/
		int num=1000000;
		List<Map<Integer, Boolean>> list=new ArrayList<>(num);  
	    for (int i = 0; i < num; i++) {  
	        Map<Integer, Boolean> passedMap = new HashMap<>();  
	        list.add(passedMap);  
	    }  
		System.out.println("===========" + list.size() + "==="
				+ (System.currentTimeMillis() - start));
		Thread.sleep(1000000);

	}
}
