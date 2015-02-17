package com.netty.async;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ResponseFuture {
	private static final ConcurrentHashMap<String, ResponseFuture>
    futures = new ConcurrentHashMap<String, ResponseFuture>();

private final Lock lock = new ReentrantLock();

private final Condition done = lock.newCondition();

private RespBean response;

public ResponseFuture(ReqBean request) {
   futures.put(request.getId(), this);
}

public String get(String id) {
   lock.lock();  
   try {
       // 可以增加超时机制
       while (!isDone()) {
           done.await(4000,TimeUnit.MILLISECONDS);
           if(null == response){
        	   futures.remove(id);
               System.out.println("==============remove");
               break;
           }
          
       }
   } catch (InterruptedException e) {
       throw new RuntimeException(e);
   }finally {
       lock.unlock();
   }
   if(response!=null){
	   return response.getRespMsg();
   }
   return null;
}

public static void received(RespBean response) {
   ResponseFuture future = futures.remove(response.getId());
   if (future != null) {
       future.doReceived(response);
   }
}

private boolean isDone() {
   return this.response != null;
}

private void doReceived(RespBean response) {
   lock.lock();
   try {
       this.response = response;
       done.signal();
   } finally {
       lock.unlock();
   }

}

}
