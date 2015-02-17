package com.disruptor.c1p1;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class DisruptorEventPublisher implements EventPublisher{

	private int bufSize;
	//创建线程池  
    ExecutorService executors =null;
    Disruptor<C1p1> disruptor=null;
    CounterTracer tracer;
	public DisruptorEventPublisher(int bufSize,CounterTracer tracer){
		executors = Executors.newFixedThreadPool(4); 
		this.bufSize = bufSize;
		this.tracer =tracer;
	        
		
	}
	@Override
	public void start() {
		disruptor=new Disruptor<C1p1>(new EventFactory<C1p1>() {  
            @Override  
            public C1p1 newInstance() {  
                return new C1p1();  
            }  
        }, bufSize, executors, ProducerType.SINGLE, new YieldingWaitStrategy());  
          
	 disruptor.handleEventsWith(new C1p1Processor());
	 disruptor.start();//启动
		
	}

	@Override
	public void handle() {
		tracer.start();
		// TODO Auto-generated method stub
		//CountDownLatch latch=new CountDownLatch(1);
		executors.submit(new Runnable() {
			
			@Override
			public void run() {
                for(int i=0;i<100000000;i++){  
                    disruptor.publishEvent(new EventTranslator<C1p1>() {
						
						@Override
						public void translateTo(C1p1 event, long sequence) {
							//Random random=new Random(); 
							event.setId(1);
							
						}
					});//发布这个区块的数据使handler(consumer)可见  
                } 
                System.out.println("====================");
                tracer.countDown();
			}
		});
	}
	@Override
	public void stop() {
		disruptor.shutdown();  
        executors.shutdown();  
		
	}

}
