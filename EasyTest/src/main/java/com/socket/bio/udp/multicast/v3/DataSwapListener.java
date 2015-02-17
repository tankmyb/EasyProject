package com.socket.bio.udp.multicast.v3;

public interface DataSwapListener extends java.util.EventListener{
	public void OnDataSendFinished(Object s,DataSwapEvent e);
	public void OnDataRecvFinished(Object s,DataSwapEvent e);
	}


