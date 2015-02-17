package com.socket.nio.nioSession;

import java.util.TimerTask;

/** 
 * 守护任务,每隔一段时间检查服务器情况 
 * 1.检查是否有残留的超时连接 
 * 2. 
 * 3. 
 * 4. 
 * @author Ritsky 
 * 
 */  
public class DaemonTask extends TimerTask {  
  
    //默认的间隔时间   
    private static final long DEFAULT_INTERVAL = 10 * 60 * 1000;  
      
    private MultiNioServer server = null;  
      
    private long interval = 0;         //间隔时间   
      
      
    DaemonTask(MultiNioServer _server) {  
        this(_server, DEFAULT_INTERVAL);  
    }  
      
    DaemonTask(MultiNioServer _server, long _interval) {  
        this.server = _server;  
        this.interval = _interval;  
    }  
      
    @Override  
    public void run() {  
        checkRudimentalTimeOutSession();  
    }  
  
      
    /** 
     * 检查残留的超时Session 
     */  
    private void checkRudimentalTimeOutSession() {  
        ReadWriteThread[] rwSelector = server.rwSelector;  
        if(rwSelector == null) {  
            return;  
        }  
          
        for(int i=0; i<rwSelector.length; i++) {  
            if(rwSelector[i] == null) {  
                continue;  
            }  
          
            if(rwSelector[i].timeoutSession.size() > 0) {  
                rwSelector[i].selector.wakeup();  
            }  
        }  
    }  
       
      
    void start() {  
        server.getTimer().schedule(this, interval, interval);  
    }  
}  

