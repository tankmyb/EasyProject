package com.disruptor.test;

import com.lmax.disruptor.EventHandler;

public class MyEventHandler implements EventHandler<ValueEvent>{  
  
    public long count = 0;  
  
    public void onEvent(ValueEvent arg0, long arg1, boolean arg2)  
            throws Exception {  
        arg0.getValue();  
        //  为了公平这里什么都不做  
    }  
}  