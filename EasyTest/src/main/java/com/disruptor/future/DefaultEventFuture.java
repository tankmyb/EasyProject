package com.disruptor.future;

import java.util.Map;

public class DefaultEventFuture implements EventFuture {

	private Map<Object, Object> attachment;
	private EventFutureListener listener;
	private boolean done;

	@Override
	public void addListener(EventFutureListener listener) {
		if (listener == null) {
			throw new NullPointerException("listener");
		}
		boolean notifyNow = false;
		synchronized (this) {
			if (done) {
				notifyNow = true;
			}
			this.listener = listener;
		}
		if (notifyNow) {
			notifyListener();
		}
	}

	@Override
	public boolean setSuccess() {
		synchronized (this) {
			if (done) {
				return false;
			}
			done = true;
		}
		notifyListener();
		return true;
	}

	private void notifyListener() {
		try {
			if(listener!=null){
				listener.operationComplete(this);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
