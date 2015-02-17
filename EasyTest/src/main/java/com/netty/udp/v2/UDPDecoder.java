package com.netty.udp.v2;
import org.jboss.netty.buffer.ChannelBuffer;  
import org.jboss.netty.channel.Channel;  
import org.jboss.netty.channel.ChannelHandlerContext;  
import org.jboss.netty.handler.codec.frame.FrameDecoder;  
  
class UDPDecoder extends FrameDecoder {  
  
    private int size;  
  
    public UDPDecoder(int size) {  
        super();  
        this.size = size;  
    }  
  
    @Override  
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {  
        byte[] body = new byte[size];  
        buffer.readBytes(body);  
  
        return new String(body, "UTF-8").trim();  
    }  
}  