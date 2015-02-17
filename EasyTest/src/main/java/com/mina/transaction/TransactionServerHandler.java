package com.mina.transaction;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class TransactionServerHandler extends IoHandlerAdapter {
	
	private final Map<String, IoSession> bidMap = new ConcurrentHashMap<String, IoSession>();
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("close");
		String bid = (String) session.getAttribute("bid");
		bidMap.remove(bid);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		session.close(true);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println(message);
		if(message instanceof String){
			String bid=(String)message;
			if(!bid.equals("heartbeat") &&!bidMap.containsKey(bid)){
				 session.setAttribute("bid", bid);
		         bidMap.put(bid, session);
			}
		}else if(message instanceof List){
			List<String> list = (List<String>)message;
			System.out.println(bidMap);
			for(String bid:list){
				if(bidMap.containsKey(bid)){
					//System.out.println(bid);
					bidMap.get(bid).write("run");
				}
			}
		}
	}
		

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageSent(session, message);
	}


}
