package com.guava;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class CopyOfCallBackTest {

	public static void main(String[] args) {
		//System.out.println("start:"+new Date());
		// 除了ListenableFuture,guava还提供了FutureCallback接口,相对来说更加方便一些.
        ListeningExecutorService guavaExecutor2 = MoreExecutors
                .listeningDecorator(Executors.newCachedThreadPool());
        	final ListenableFuture<String> listenableFuture2 = guavaExecutor2
                    .submit(new Callable<String>() {

                        @Override
                        public String call() throws Exception {
                            Thread.sleep(5000);
                            //System.out.println(Thread.currentThread().getName());
                            return Thread.currentThread().getName();
                        }
                    });
        
            // 注意这里没用指定执行回调的线程池,从输出可以看出，默认是和执行异步操作的线程是同一个.
            Futures.addCallback(listenableFuture2, new FutureCallback<String>() {

                @Override
                public void onSuccess(String result) {
                	try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    System.out.println(Thread.currentThread().getName()+"=1==1=="+result);
                }
                @Override
                public void onFailure(Throwable t) {
                }
            });
            
        guavaExecutor2.shutdown();
        System.out.println("==============="+new Date());
	}
}
