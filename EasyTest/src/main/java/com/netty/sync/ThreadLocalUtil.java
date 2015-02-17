package com.netty.sync;


public class ThreadLocalUtil {

	private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();
	
	public static void set(String value) {
		String v = threadLocal.get();
		if (v == null) {
			threadLocal.set(v);
		}
	}

	
	public static Object get() {
		return threadLocal.get();
	}

}
