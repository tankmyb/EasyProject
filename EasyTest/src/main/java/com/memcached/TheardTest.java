package com.memcached;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TheardTest {

	public static void main(String[] args) {
		final IMemcacheHandler handler = MemcacheHandler.getInstance();
		boolean a = handler.addObj("aaaaaaaa", "first");
		System.out.println("add first:"+a);
		ExecutorService cachedService = Executors.newCachedThreadPool();
		cachedService.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("====get first===");
				Object obj = handler.getObj("aaaaaaaa");
				System.out.println("get first:"+obj);
			}
		});
		cachedService.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("====update==1=");
				handler.updateObj("aaaaaaaa", "second");
				System.out.println("====update==2=");
			}
		});
		cachedService.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("====get second===");
				Object obj = handler.getObj("aaaaaaaa");
				System.out.println("get second:"+obj);
			}
		});
		cachedService.shutdown();
	}
}
