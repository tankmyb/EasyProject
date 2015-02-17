package com.netty.message;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class MessageDecoder extends FrameDecoder {
 
    @Override
    protected Object decode(
            ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        if (buffer.readableBytes() < 4) {
            return null;//(1)
        }
        System.out.println(buffer.capacity());
        int dataLength = buffer.getInt(buffer.readerIndex());
        System.out.println(dataLength+"==="+buffer.readableBytes());
        if (buffer.readableBytes() < dataLength + 4) {
            return null;//(2)
        }
 
        buffer.skipBytes(4);//(3)
        byte[] decoded = new byte[dataLength];
        System.out.println("===");
        buffer.readBytes(decoded);
        String msg = new String(decoded);//(4)
        //channel.write("aaaaaaaaaa");
        return msg;
    }
}
