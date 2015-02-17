package com.disruptor.v1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.WorkerPool;

public class Demo2 {  
    public static void main(String[] args) throws InterruptedException {  
        int BUFFER_SIZE=1024;  
        int THREAD_NUMBERS=4;  
        EventFactory<TradeTransaction> eventFactory=new EventFactory<TradeTransaction>() {  
            public TradeTransaction newInstance() {  
                return new TradeTransaction();  
            }  
        };  
        RingBuffer<TradeTransaction> ringBuffer=RingBuffer.createSingleProducer(eventFactory, BUFFER_SIZE);  
          
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();  
          
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBERS);  
          
        WorkHandler<TradeTransaction> workHandlers=new TradeTransactionInDBHandler();  
        /* 
         * 这个类代码很简单的，亲自己看哈！~ 
         */  
        WorkerPool<TradeTransaction> workerPool=new WorkerPool<TradeTransaction>(ringBuffer, sequenceBarrier, new IgnoreExceptionHandler(), workHandlers);  
          
        workerPool.start(executor);  
          
        //下面这个生产8个数据，图简单就写到主线程算了  
        for(int i=0;i<8;i++){  
            long seq=ringBuffer.next();  
            ringBuffer.get(seq).setPrice(Math.random()*9999);  
            ringBuffer.publish(seq);  
        }  
          
        Thread.sleep(1000);  
        workerPool.halt();  
        executor.shutdown();  
    }  
}  