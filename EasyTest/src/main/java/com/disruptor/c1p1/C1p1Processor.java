package com.disruptor.c1p1;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class C1p1Processor implements WorkHandler<C1p1>,EventHandler<C1p1>{

	@Override
	public void onEvent(C1p1 event, long sequence, boolean endOfBatch)
			throws Exception {
		this.onEvent(event);
		
	}

	@Override
	public void onEvent(C1p1 event) throws Exception {
		//System.out.println(event.getId());
		
	}

}
