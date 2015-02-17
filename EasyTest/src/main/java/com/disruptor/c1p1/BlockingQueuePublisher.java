package com.disruptor.c1p1;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueuePublisher  {
    int size=10000000;
    private ArrayBlockingQueue<C1p1> queue ;    
    CounterTracer tracer = new SimpleTracer(size);
    public BlockingQueuePublisher(int maxEventSize) {
        this.queue = new ArrayBlockingQueue<C1p1>(maxEventSize);
    }

    public void start(){
    	tracer.start();
        Thread thrd = new Thread(new Runnable() {
            @Override
            public void run() {
                handle();
            }
        });
        thrd.start();
        new Thread(new Runnable() {
			@Override
			public void run() {
				try {
				for(int i=0;i<size;i++){
						publish(i);
				}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
    }
    
    private void handle(){
        try {
            C1p1 evt ;
            while (true) {
                evt = queue.take();
                tracer.countDown();
                if (evt.getId()==(size-1)) {
                    //完成后自动结束处理线程；
                	System.out.println(tracer.getMilliTimeSpan());
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void publish(int data) throws Exception {
        C1p1 evt = new C1p1();
        evt.setId(data);
        queue.put(evt);
    }

    public static void main(String[] args) {
    	BlockingQueuePublisher b = new BlockingQueuePublisher(1000);
    	b.start();
    	
	}
    //省略其它代码；
}