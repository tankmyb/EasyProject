package com.socket.nio.nioSession;

/** 
 * 逻缉处理接口，由逻缉程序员实现 
 *  
 * @author Ritsky 
 * 
 */  
public interface ServerLogicProcess {  
      
    /** 
     * 当有消息进入时进行处理的方法 
     * @param message 
     * @return 
     */  
    public Object serverProcess(NetMessage message);  
      
      
    /** 
     * 当连接关闭时 
     * @param session 
     */  
    public void onConnectClose(SocketSession session);  
      
      
    /** 
     * 当连接打开时 
     * @param session 
     */  
    public void onConnectOpen(SocketSession session);  
}  

