package com.netty.kryo.v1;
import java.util.Date;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.netty.kryo.pojo.Person;
  
public class ClientHandler extends SimpleChannelHandler {  
  
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)  
            throws Exception {  
        Person content = (Person) e.getMessage();  
        System.out.println(""+ new Date().toString() + "\n" + content.getName());  
    }  
}  