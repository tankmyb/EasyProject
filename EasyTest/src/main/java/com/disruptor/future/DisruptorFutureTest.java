package com.disruptor.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.disruptor.v1.TradeTransaction;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class DisruptorFutureTest {

	public static void main(String[] args) {
		int bufferSize=1024;  
        ExecutorService executor=Executors.newFixedThreadPool(4); 
      //这个构造函数参数，相信你在了解上面2个demo之后就看下就明白了，不解释了~  
        Disruptor<Event> disruptor=new Disruptor<Event>(new EventFactory<Event>() {  
            @Override  
            public Event newInstance() {  
                return new Event();  
            }  
        }, bufferSize, executor, ProducerType.SINGLE, new BusySpinWaitStrategy());
        disruptor.handleEventsWith(new DisruptorFutureProcessor());
        disruptor.start();
        
        DisruptorPublisher publisher = new DisruptorPublisher(disruptor);
        EventFuture future = publisher.publishEvent(1);
        future.addListener(new EventFutureListener() {
			@Override
			public void operationComplete(EventFuture future) throws Exception {
				System.out.println("============================");
			}
		});
	}
}
