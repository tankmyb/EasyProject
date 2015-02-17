package com.socket.nio.nioSession;

/** 
 * <p> 
 * 自定义头 <br> 
 * 头一般为协议的组成部分，但头的格式不同的通讯协议有不同的定义，这里允许<br> 
 * 程序员通过实现此接口定义自已的头格式 
 * </p> 
 * <br> 
 *  
 * @author Ritsky 
 * 
 */  
public interface CustomMsgHeader {  
      
    /** 
     * <p> 
     * 编码，把头组装成字节数组 
     * </p> 
     * <br> 
     * @return byte[] 字节数组<br> 
     */  
    public byte[] encode();  
      
      
    /** 
     * <p> 
     * 解码，把字节数组解析成头 
     * </p> 
     * <br> 
     * @param data 字节数组<br> 
     */  
    public void decode(byte[] data);  
}  

