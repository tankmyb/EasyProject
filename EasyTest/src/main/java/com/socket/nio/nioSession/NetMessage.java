package com.socket.nio.nioSession;

/** 
 * <p> 
 * 网络消息接口，消息中具体有哪些内容由使用者定义 
 * </p> 
 * <br> 
 * @author Ritsky 
 * 
 */  
public interface NetMessage {  
  
    /** 
     * <p> 
     * 获取自定义头 
     * </p> 
     * <br> 
     * @return CustomMsgHeader 
     */  
    public CustomMsgHeader getHeader();  
      
    public byte[] getBody();   
}  
