package com.disruptor.c1p1;

import java.util.concurrent.ExecutionException;

public class Demo {  
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int BUFFER_SIZE=4096;  
        CounterTracer tracer = new SimpleTracer(1);
        EventPublisher p = new DisruptorEventPublisher(BUFFER_SIZE,tracer);
        p.start();
        p.handle();
        tracer.await();
        System.out.println(tracer.getMilliTimeSpan());
        p.stop();
    }  
}  