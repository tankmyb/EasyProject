package com.guava.callback.v2;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public class Futures {

	public static <T> void addFuture(final MyFuture<T> future,final Callback<T> callback,Executor executor){
		future.setExecutor(executor);
		System.out.println("==executor="+executor);
		future.setRunnable(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("开始等待"+Thread.currentThread().getName());
					T t = getUninterruptibly(future);
					System.out.println("结束等待"+Thread.currentThread().getName());
					callback.onSuccess(t);
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	 public static <V> V getUninterruptibly(Future<V> future)
		      throws ExecutionException {
		    boolean interrupted = false;
		    try {
		      while (true) {
		        try {
		          return future.get();
		        } catch (InterruptedException e) {
		          interrupted = true;
		        }
		      }
		    } finally {
		      if (interrupted) {
		        Thread.currentThread().interrupt();
		      }
		    }
		  }
}
