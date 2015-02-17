package com.disruptor.v1;

import java.util.UUID;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class TradeTransactionInDBHandler implements EventHandler<TradeTransaction>,WorkHandler<TradeTransaction> {  
  
    @Override  
    public void onEvent(TradeTransaction event, long sequence,  
            boolean endOfBatch) throws Exception {  
        this.onEvent(event);  
    }  
  
    @Override  
    public void onEvent(TradeTransaction event) throws Exception {  
        //这里做具体的消费逻辑  
        event.setId(UUID.randomUUID().toString());//简单生成下ID  
        //System.out.println(Thread.currentThread().getName()+"==="+System.currentTimeMillis());
        System.out.println(event.getPrice()+"=="+event.getId());
        event.setPrice(event.getPrice()/2);
        System.out.println(event.getPrice()+"=="+event.getId());
        
    }  
}  