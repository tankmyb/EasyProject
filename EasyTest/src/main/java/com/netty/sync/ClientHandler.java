package com.netty.sync;
import java.util.Date;  
  
import org.jboss.netty.channel.ChannelHandlerContext;  
import org.jboss.netty.channel.MessageEvent;  
import org.jboss.netty.channel.SimpleChannelHandler;  
  
public class ClientHandler extends SimpleChannelHandler {  
  
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)  
            throws Exception {  
        String content = (String) e.getMessage();  
        ThreadLocalUtil.set(content);
        System.out.println(""+ new Date().toString() + "\n" + content);  
    }  
}  