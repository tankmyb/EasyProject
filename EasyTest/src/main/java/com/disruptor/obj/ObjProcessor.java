package com.disruptor.obj;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class ObjProcessor implements WorkHandler<Obj>,EventHandler<Obj>{

	@Override
	public void onEvent(Obj event, long sequence, boolean endOfBatch)
			throws Exception {
		this.onEvent(event);
		
	}

	@Override
	public void onEvent(Obj event) throws Exception {
		if(event.getObj() instanceof O1){
			O1 o = (O1)event.getObj();
			System.out.println(o.getName());
		}else if(event.getObj() instanceof O2){
			O2 o = (O2)event.getObj();
			System.out.println(o.getAge());
		}
		System.out.println(event.getObj().getClass());
		
	}

}
