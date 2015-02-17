package com.disruptor.c1p1;

import java.util.concurrent.CountDownLatch;

/**
 * 测试结果跟踪器，计数器不是线程安全的，仅在单线程的 consumer 测试中使用；
 * 
 * @author haiq
 *
 */
public class SimpleTracer implements CounterTracer {

    private long startTicks;
    private long endTicks;
    private CountDownLatch latch;

    public SimpleTracer(int size) {
        latch = new CountDownLatch(size);
    }

    @Override
    public void start() {
        startTicks = System.currentTimeMillis();
    }

    @Override
    public long getMilliTimeSpan() {
        return endTicks - startTicks;
    }

    @Override
    public void countDown() {
            latch.countDown();
            if(latch.getCount()==0){
            	endTicks = System.currentTimeMillis();
            }
        
    }

    @Override
    public void await() throws InterruptedException {
        latch.await();
    }
}