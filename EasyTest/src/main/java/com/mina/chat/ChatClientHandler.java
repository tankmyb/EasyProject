package com.mina.chat;

import java.util.Map;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ChatClientHandler extends IoHandlerAdapter{  
	  
	  @Override  
	  public void exceptionCaught(IoSession arg0, Throwable arg1)  
	          throws Exception {  
	      // TODO Auto-generated method stub  

	  }  

	  /** 
	   * 当客户端接受到消息时 
	   */  
	  @Override  
	  public void messageReceived(IoSession session, Object message) throws Exception {
		  
	  }  

	  @Override  
	  public void messageSent(IoSession arg0, Object arg1) throws Exception {  
	      // TODO Auto-generated method stub  

	  }  

	  /** 
	   * 当一个客户端被关闭时 
	   */  
	  @Override  
	  public void sessionClosed(IoSession session) throws Exception {  
	      System.out.println("one client Disconnect");  

	  }  

	  @Override  
	  public void sessionCreated(IoSession session) throws Exception {  
	      // TODO Auto-generated method stub  
	  	session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
	  }  

	  @Override  
	  public void sessionIdle(IoSession session, IdleStatus status) throws Exception {  
	 // 如果IoSession闲置，则关闭连接
	    if (status == IdleStatus.BOTH_IDLE)
	    {
	     //session.write("heartbeat");
	    }
	  }  

	  /** 
	   * 当一个客户端连接进入时 
	   */  
	  @Override  
	  public void sessionOpened(IoSession session) throws Exception {  

	      System.out.println("incomming client:" + session.getRemoteAddress());  
	      session.write("我来啦");  

	  }  



}
