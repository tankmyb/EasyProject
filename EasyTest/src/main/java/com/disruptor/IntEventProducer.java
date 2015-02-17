package com.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

//生产者  
public class IntEventProducer implements EventHandler<IntEvent>,WorkHandler<IntEvent> {  
  
    private int seq = 0;  
    public void onEvent(IntEvent event) throws Exception {  
        System.out.println("produced " + seq);  
        event.setValue(++seq);  
    }
	@Override
	public void onEvent(IntEvent event, long sequence, boolean endOfBatch)
			throws Exception {
		onEvent(event);
		
	}  
  
}  