package com.disruptor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.WorkerPool;

public class DisruptorTest {  
	
    public static void main(String[] args) throws InterruptedException, ExecutionException {  
        //创建一个RingBuffer对象  
        final RingBuffer<IntEvent> ringBuffer = RingBuffer.createSingleProducer(IntEvent.INT_ENEVT_FACTORY,  
            1024,  
            new SleepingWaitStrategy());  
        ExecutorService executor = Executors.newFixedThreadPool(4);  
        
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();  
        WorkHandler<IntEvent> processors =new IntEventProcessor();
        /* 
         * 这个类代码很简单的，亲自己看哈！~ 
         */  
        WorkerPool<IntEvent> workerPool = new WorkerPool<IntEvent>(ringBuffer,  
            sequenceBarrier,  
            new IgnoreExceptionHandler(),  
            processors);  
        workerPool.start(executor);  
        Future<?> future=executor.submit(new Callable<Void>() {  
            @Override  
            public Void call() throws Exception {  
                long seq;  
                for(int i=0;i<1000;i++){  
                    seq=ringBuffer.next();//占个坑 --ringBuffer一个可用区块  
                    ringBuffer.get(seq).setValue(i);//给这个区块放入 数据  如果此处不理解，想想RingBuffer的结构图  
                    ringBuffer.publish(seq);//发布这个区块的数据使handler(consumer)可见  
                }  
                return null;  
            }  
        });  
        future.get();//等待生产者结束  
        Thread.sleep(1000);//等上1秒，等消费都处理完成  
        workerPool.halt();  
        executor.shutdown();  
    }  
}  