package com.mina.heartbeat.v2;

import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ClientHander extends IoHandlerAdapter {  
  
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

      //我们已设定了服务器的消息规则是一行一行读取，这里就可以转为String:  
  	try {
  		//TestBean s = (TestBean)message;  
     // System.out.println("服务器发来的收到消息: " + s);  
      
      //Map s = (Map)message;  
      //System.out.println("服务器发来的收到消息: " + s.get("name"));  
  		
  		String s = (String)message;
  		System.out.println("服务器发来的收到消息: "+s);
		} catch (Exception e) {
			// TODO: handle exception
		}
      
        
      //测试将消息回送给客户端  
      //session.write(s);  

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
    	System.out.println("=====");
    	final WriteFuture future = session.write("heartbeat");
    	future.addListener(new IoFutureListener(){  
               public void operationComplete(IoFuture future1) {  
                   System.err.println("isWriten:"   
                           + future.isWritten() );  
               }  
           }); 
    	/*future.awaitUninterruptibly(3000); // 等待发送数据操作完成
    	if (future.getException() != null) {  
        throw new Exception(future.getException().getMessage());  
    } 
    	if(future.isWritten()) 
    	{ 
    	    System.err.println("success");
    	} 
    	else 
    	{ 
    		System.err.println("failure");
    	}*/
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
