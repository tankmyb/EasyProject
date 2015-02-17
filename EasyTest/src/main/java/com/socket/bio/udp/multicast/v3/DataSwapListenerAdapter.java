package com.socket.bio.udp.multicast.v3;

import java.util.ArrayList;
import java.util.Iterator;

public class DataSwapListenerAdapter implements DataSwapListener{
	protected ArrayList listener;
	public DataSwapListenerAdapter() {
	    listener = new ArrayList();
	}

	protected void addDataSwapListener(DataSwapListener dsl) {
	    listener.add(dsl);
	}

	protected void removeDataSwapListener(DataSwapListener dsl) {
	    listener.remove(dsl);
	}

	protected void processRecvFinishedEvent(DataSwapEvent e) {
	    for (int i = 0; i < this.listener.size(); i++) {
	      ( (DataSwapListener)this.listener.get(i)).OnDataRecvFinished(this, e);
	    }
	}

	protected void processSendFinished(DataSwapEvent e) {
	    for (int i = 0; i < this.listener.size(); i++) {
	      ( (DataSwapListener)this.listener.get(i)).OnDataSendFinished(this, e);
	    }
	}

	public void OnDataSendFinished(Object s, DataSwapEvent e) {
	    Iterator it = listener.iterator();
	    while (it.hasNext())
	      ( (DataSwapListener) it.next()).OnDataRecvFinished(s, e);
	}

	public void OnDataRecvFinished(Object s, DataSwapEvent e) {
	    Iterator it = listener.iterator();
	    while (it.hasNext())
	      ( (DataSwapListener) it.next()).OnDataRecvFinished(s, e);
	}



}
