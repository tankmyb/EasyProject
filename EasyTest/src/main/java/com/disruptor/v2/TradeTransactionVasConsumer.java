package com.disruptor.v2;

import com.disruptor.v1.TradeTransaction;
import com.lmax.disruptor.EventHandler;

public class TradeTransactionVasConsumer implements EventHandler<TradeTransaction> {  
  
    @Override  
    public void onEvent(TradeTransaction event, long sequence,  
            boolean endOfBatch) throws Exception {  
        //do something....  
    	event.setPrice(event.getPrice()/3);
    	System.out.println("===============c2==="+event.getPrice());
    }  
      
}  