package com.guava;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class CallBackTest1 {

	public static void main(String[] args) {
		System.out.println("start:"+new Date());
		// 除了ListenableFuture,guava还提供了FutureCallback接口,相对来说更加方便一些.
        ListeningExecutorService guavaExecutor2 = MoreExecutors
                .listeningDecorator(Executors.newCachedThreadPool());
        final ListenableFuture<String> listenableFuture2 = guavaExecutor2
                .submit(new Callable<String>() {

                    @Override
                    public String call() throws Exception {
                        Thread.sleep(1000);
                        System.out.println("asyncThreadName:"
                                + Thread.currentThread().getName());
                        return "this is guava future call.support async callback using FutureCallback";
                    }
                });
        guavaExecutor2.execute(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(new Date()+"======="+Thread.currentThread().getName());
				try {
					System.out.println(Thread.currentThread().getName()+"==="+listenableFuture2.get());
					
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
        
        System.out.println("==============="+new Date());
	}
}
