package com.disruptor.future;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

public class DisruptorPublisher {

	private Disruptor<Event> disruptor;
	public DisruptorPublisher(Disruptor<Event> disruptor){
		this.disruptor = disruptor;
	}
	public EventFuture publishEvent(final Object data){
		final EventFuture future = new DefaultEventFuture();
		disruptor.publishEvent(new EventTranslator<Event>() {
			@Override
			public void translateTo(Event event, long sequence) {
				event.setData(data);
				event.setFuture(future);
			}
		});
		return future;
	}
	
}
