package com.disruptor.v2;

import com.disruptor.v1.TradeTransaction;
import com.lmax.disruptor.EventHandler;

public class TradeTransactionJMSNotifyHandler implements EventHandler<TradeTransaction> {  
  
    @Override  
    public void onEvent(TradeTransaction event, long sequence,  
            boolean endOfBatch) throws Exception {  
        //do send jms message
    	System.out.println("=-====jms");
    }  
}  