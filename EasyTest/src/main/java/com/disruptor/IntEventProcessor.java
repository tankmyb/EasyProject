package com.disruptor;

import com.lmax.disruptor.WorkHandler;

//消费者  
public class IntEventProcessor implements WorkHandler<IntEvent> {  
  
    public void onEvent(IntEvent event) throws Exception {  
        System.out.println(event.getValue());  
        event.setValue(1);  
    }  
  
}  