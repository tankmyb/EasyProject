package com.socket.nio.nioSession;

/** 
 * <p> 
 * 网络事件监听者 
 * </p> 
 * <br> 
 *  
 * @author Ritsky 
 * 
 */  
public interface NetEventListener {  
      
    /** 
     * <p> 
     * SocketSession被创建时执行 
     * </p> 
     * <br> 
     * @param session 
     */  
    public void onSocketSessionCreated(SocketSession session);  
      
    /** 
     * <p> 
     * SocketSession发生写时执行 
     * </p> 
     * <br> 
     * @param session 
     */  
    public void onSocketSessionWrited(SocketSession session);  
      
      
    /** 
     * <p> 
     * SocketSession发生读时执行 
     * </p> 
     * <br> 
     * @param session 
     */  
    public void onSocketSessionReaded(SocketSession session);  
}  

