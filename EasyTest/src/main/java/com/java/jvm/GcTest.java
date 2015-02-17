package com.java.jvm;

import java.util.HashMap;
import java.util.Map;

public class GcTest {

	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(20000);
		Map<String,byte[]> map = new HashMap<String, byte[]>();
		map.put("1",new byte[2*1024*1024]);
	    Thread.sleep(2000);
	    System.out.println("========2=========");
	    map.put("2",new byte[2*1024*1024]);
	    Thread.sleep(2000);
	    System.out.println("==========3=======");
	    map.put("3",new byte[2*1024*1024]);
	    Thread.sleep(2000);
	    System.out.println("==========4=======");
	    map.put("4",new byte[2*1024*1024]);
	    Thread.sleep(2000);
	    System.out.println("============5=====");
	    map.put("5",new byte[2*1024*1024]);
	    System.out.println("==============6===");
	    Thread.sleep(20000);
	    map.remove("5");
	    map.remove("4");
	    map.remove("3");
	    System.out.println("=========7========");
	    Thread.sleep(200000);
	}
}
