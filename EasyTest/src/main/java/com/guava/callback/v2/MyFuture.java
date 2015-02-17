package com.guava.callback.v2;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

public class MyFuture<V> extends FutureTask<V>{

	private Runnable runnable;
	private Executor executor;
	
	public static <V> MyFuture<V> create(Callable<V> callable) {
	    return new MyFuture<V>(callable);
	  }

	  /**
	   * Creates a {@code ListenableFutureTask} that will upon running, execute the
	   * given {@code Runnable}, and arrange that {@code get} will return the
	   * given result on successful completion.
	   *
	   * @param runnable the runnable task
	   * @param result the result to return on successful completion. If you don't
	   *     need a particular result, consider using constructions of the form:
	   *     {@code ListenableFuture<?> f = ListenableFutureTask.create(runnable,
	   *     null)}
	   * @since 10.0
	   */
	  public static <V> MyFuture<V> create(
	      Runnable runnable,  V result) {
	    return new MyFuture<V>(runnable, result);
	  }
	public MyFuture(Callable<V> callable) {
		super(callable);
	}
	MyFuture(Runnable runnable,  V result) {
	    super(runnable, result);
	  }
	public void setRunnable(Runnable runnable){
		this.runnable = runnable;
	}
	public void setExecutor(Executor executor){
		this.executor = executor;
	}
	@Override
	protected void done() {
		System.out.println(executor+"==============");
		try{
			executor.execute(runnable);
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		
	}
	
}
