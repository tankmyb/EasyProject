package com.guava.callback.v2;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Test {
	private static  class ListeningDecorator
    extends AbstractMyExecutorService {
  private final ExecutorService delegate;

  ListeningDecorator(ExecutorService delegate) {
    this.delegate = delegate;
  }

  @Override
  public boolean awaitTermination(long timeout, TimeUnit unit)
      throws InterruptedException {
    return delegate.awaitTermination(timeout, unit);
  }

  @Override
  public boolean isShutdown() {
    return delegate.isShutdown();
  }

  @Override
  public boolean isTerminated() {
    return delegate.isTerminated();
  }

  @Override
  public void shutdown() {
    delegate.shutdown();
  }

  @Override
  public List<Runnable> shutdownNow() {
    return delegate.shutdownNow();
  }

  @Override
  public void execute(Runnable command) {
    delegate.execute(command);
  }
}
	public static MyExecutorService listeningDecorator(
		      ExecutorService delegate) {
			    return  new ListeningDecorator(delegate);
		  }
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		
		MyExecutorService es = listeningDecorator(Executors.newCachedThreadPool());
		MyFuture<String> submit = es.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				//System.out.println(new Date()+"开始工作"+Thread.currentThread().getName());
				Thread.sleep(5000L);
				//System.out.println("结束工作"+Thread.currentThread().getName());
				return Thread.currentThread().getName();
			}
			
		});
		Futures.addFuture(submit, new Callback<String>() {

			@Override
			public void onSuccess(String value) {
				System.out.println(Thread.currentThread().getName()+"===="+value+"========"+new Date());
				
			}
		}, es);
	        //es.shutdown();
	        System.out.println("===============2");
	}
}
