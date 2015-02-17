package com.disruptor.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.disruptor.obj.Obj;
import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.YieldingWaitStrategy;

/**  
 * @author shenbaise  
 */  
public class RbTest {  
          
    private static final int BUFFER_SIZE = 4096;  
      
    public static void main(String[] args) {  
        RbTest test = new RbTest();  
        test.test();  
    }  
      
    public void test() {  
        long cost = System.currentTimeMillis();  
        final RingBuffer<ValueEvent> ringBuffer = RingBuffer.createSingleProducer(ValueEvent.EVENT_FACTORY, BUFFER_SIZE,new YieldingWaitStrategy());  
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();  
        MyEventHandler handler = new MyEventHandler();  
        BatchEventProcessor<ValueEvent> batchEventProcessor =   
            new BatchEventProcessor<ValueEvent>(ringBuffer, sequenceBarrier, handler);  
        ringBuffer.addGatingSequences(batchEventProcessor.getSequence());  
        ExecutorService executor = Executors.newSingleThreadExecutor();  
      executor.submit(batchEventProcessor);  
    final RingBuffer<ValueEvent> rb = ringBuffer;  
        for (long i = 0; i < 100000000; i++)  
        {  
            long next = rb.next();  
            rb.get(next).setValue(i);  
            rb.publish(next);  
        }  
        batchEventProcessor.halt();  
        System.out.println("cost:"+(System.currentTimeMillis() - cost));  
    }  
}  