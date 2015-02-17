package com.disruptor.future;

import com.disruptor.obj.Obj;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class DisruptorFutureProcessor implements WorkHandler<Event>,EventHandler<Event>{

	@Override
	public void onEvent(Event event, long sequence, boolean endOfBatch)
			throws Exception {
		this.onEvent(event);
		
	}

	@Override
	public void onEvent(Event event) throws Exception {
		System.out.println("=====succ");
		event.getFuture().setSuccess();
		
	}

}
