package com.netty.selectsend.v1;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public class MessageEncoder extends OneToOneEncoder {
 
    @Override
    protected Object encode(
            ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        if (!(msg instanceof String)) {
            return msg;//(1)
        }
 
        String res = (String)msg;
        byte[] data = res.getBytes();
        int dataLength = data.length;
        ChannelBuffer buf = ChannelBuffers.dynamicBuffer();//(2)
        buf.writeInt(dataLength);
        buf.writeBytes(data);
        return buf;//(3)
    }
}