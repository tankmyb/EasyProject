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

public class CallBackTest {

	public static void main(String[] args) {
		//System.out.println("start:"+new Date());
		long startTime = System.currentTimeMillis();
		// 除了ListenableFuture,guava还提供了FutureCallback接口,相对来说更加方便一些.
		int size=100000;
		CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch end = new CountDownLatch(size);
        ListeningExecutorService guavaExecutor2 = MoreExecutors
                .listeningDecorator(Executors.newCachedThreadPool());
        for(int i=0;i<size;i++){
        	final int a = i;
        	final ListenableFuture<String> listenableFuture2 = guavaExecutor2
                    .submit(new Callable<String>() {

                        @Override
                        public String call() throws Exception {
                            Thread.sleep(5000);
                            //System.out.println(Thread.currentThread().getName()+"===0=="+a);
                            return a+"";
                        }
                    });
        
            // 注意这里没用指定执行回调的线程池,从输出可以看出，默认是和执行异步操作的线程是同一个.
            Futures.addCallback(listenableFuture2, new FutureCallback<String>() {

                @Override
                public void onSuccess(String result) {
                    //System.out
                           // .println("async callback(using FutureCallback) result:"
                                   // + result);
                    //System.out.println(Thread.currentThread().getName()+"===1=="+result);
                	end.countDown();
                }
                @Override
                public void onFailure(Throwable t) {
                }
            });
        
        }
        System.out.println("start");
        start.countDown();
        try {
			end.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("end:"+(System.currentTimeMillis()-startTime));
        guavaExecutor2.shutdown();
        //System.out.println("==============="+new Date());
	}
}
