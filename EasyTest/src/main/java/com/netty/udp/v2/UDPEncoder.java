package com.netty.udp.v2;
import org.jboss.netty.buffer.ChannelBuffer;  
import org.jboss.netty.buffer.ChannelBuffers;  
import org.jboss.netty.channel.Channel;  
import org.jboss.netty.channel.ChannelHandlerContext;  
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;  
  
class UDPEncoder extends OneToOneEncoder {  
  
    public static final char BLANK = ' ';  
  
    private int size;  
  
    public UDPEncoder(int size) {  
        super();  
        this.size = size;  
    }  
  
    @Override  
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {  
        if(!(msg instanceof String)){  
            return msg;  
        }  
          
        String message = (String) msg;  
        byte[] body = message.getBytes("UTF-8");  
  
        ChannelBuffer buf = ChannelBuffers.dynamicBuffer();  
        buf.writeBytes(body);  
        fill(buf, size - body.length);  
        return buf;  
    }  
  
    private void fill(ChannelBuffer buf, int size) {  
        for (int i = 0; i < size; i++) {  
            buf.writeByte(BLANK);  
        }  
    }  
  
}  