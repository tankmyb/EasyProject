package com.socket.nio.nioSession;

import java.util.TimerTask;  

/** 
 * 超时检查,用于server的定时器类 
 *  
 * @author Ritsky 
 * 
 */  
public class TimeOutCheck extends TimerTask {  
  
    SocketSession session = null;          //与连接相关的   
      
    TimeOutCheck(SocketSession _session) {  
        this.session = _session;  
    }  
      
    @Override  
    public void run() {  
          
        if(session == null) {  
            this.cancel();  
        }  
          
          
        long currTime = System.currentTimeMillis();      //获取到当前时间   
          
        if(currTime - session.getLastAccessTime() >= session.getOutTime()) {  
            //超时后的处理   
            this.cancel();    //取消超时检查任务   
              
            //把超时的SocketSession添加到归属线程的超时列表中   
            session.getOnwerThread().timeoutSession.add(session);  
              
            session.getOnwerThread().selector.wakeup();  
        }  
    }  
  
}  
