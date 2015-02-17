package com.mina.transaction;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class TransactionClientHandler extends IoHandlerAdapter{  
	  private String bid;
	  private TransactionClient client;
	  public TransactionClientHandler(TransactionClient client,String bid){
		  this.client = client;
		  this.bid = bid;
	  }
	  @Override  
	  public void exceptionCaught(IoSession session, Throwable cause)  
	          throws Exception {  
		  super.exceptionCaught(session, cause);
		 // Thread.sleep(3000L);
		  client.getConnectFuture();
		  System.out.println("===========1");
	      //session.close(true);

	  }  

	  /** 
	   * 当客户端接受到消息时 
	   */  
	  @Override  
	  public void messageReceived(IoSession session, Object message) throws Exception {
		  String msg = (String) message;
		  System.out.println("receive:"+msg);
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
	      super.sessionClosed(session);

	  }  

	  @Override  
	  public void sessionCreated(IoSession session) throws Exception {  
	    super.sessionCreated(session);
	    //session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
	  }  

	  @Override  
	  public void sessionIdle(IoSession session, IdleStatus status) throws Exception {  
	    super.sessionIdle(session, status);
	    //if (status == IdleStatus.BOTH_IDLE)
	    //{
	    // session.write("heartbeat");
	    //}
	  }  

	  /** 
	   * 当一个客户端连接进入时 
	   */  
	  @Override  
	  public void sessionOpened(IoSession session) throws Exception {  
	      session.write(bid);  

	  }  


}
