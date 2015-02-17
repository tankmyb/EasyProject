package com.memcached;

public class Test {

	public static void main(String[] args) {
		IMemcacheHandler handler = MemcacheHandler.getInstance();
		boolean a = handler.addObj("aaaaaaaa", "aaaaaa");
		System.out.println("add first:"+a);
		a = handler.addObj("aaaaaaaa", "bbbb");
		System.out.println("add second:"+a);
		System.out.println(handler.getObj("aaaaaaaa"));
	}
}
