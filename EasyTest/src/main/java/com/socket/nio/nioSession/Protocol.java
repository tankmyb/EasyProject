package com.socket.nio.nioSession;

import java.nio.ByteBuffer;  
import java.util.List;  
  
/** 
 * <p> 
 * NIO协议处理接口，不同软件可能有不同的自定义协议，这里只定义协议处理方法，实际的处<br> 
 * 理由不同的软件再去做实现 
 * </p> 
 * <br> 
 *  
 * @author Ritsky 
 * 
 */  
public interface Protocol {  
    /** 
     * <p> 
     * 解析输入数据 
     * </p> 
     * <br> 
     * @param buffer  
     * @param owner 
     * @return 
     */  
    public  List<NetMessage> parseDataInput(ByteBuffer buffer, SocketSession owner);  
      
      
    /** 
     * <p> 
     * 打包输出数据 
     * </p> 
     * <br> 
     * @param msg 
     * @return 
     */  
    public  byte[] packDataOutput(NetMessage msg);  
  
      
    public byte[] packDataOutput(byte[] data);  
      
      
    /** 
     * <p> 
     * 连接每次用完时是否关闭 
     * </p> 
     * <br> 
     * @return 
     */  
    public boolean isClose();   
}  

