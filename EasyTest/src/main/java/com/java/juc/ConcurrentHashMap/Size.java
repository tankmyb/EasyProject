package com.java.juc.ConcurrentHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Size {
    
	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(10000);
		long start= System.currentTimeMillis();
		/*String s="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
		for (int i = 0; i < 1000000; i++) {
			map.put(new String(s + ""+i),new String(s + ""+i));
			// System.out.println("======="+i);
		}*/
		int num=1000000;
		List<Map<Integer, Boolean>> list=new ArrayList<>(num);  
	    for (int i = 0; i < num; i++) {  
	        Map<Integer, Boolean> passedMap = new ConcurrentHashMap<>();  
	        list.add(passedMap);  
	    }  
		System.out.println("==========="+list.size()+"==="+(System.currentTimeMillis()-start));
		Thread.sleep(1000000);
		
	}
}
