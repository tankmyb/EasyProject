package com.netty.bigmsg;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageDecoder extends FrameDecoder {
	private  final Logger logger = LoggerFactory.getLogger(MessageDecoder.class);
    @Override
    protected Object decode(
            ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
    	//logger.info("============decode========="+channel.getRemoteAddress());
        if (buffer.readableBytes() < 4) {
            return null;//(1)
        }
        int dataLength = buffer.getInt(buffer.readerIndex());
        logger.info(Thread.currentThread().getId()+"==="+buffer.readableBytes()+"====================="+dataLength+"=="+channel.getRemoteAddress());
        if (buffer.readableBytes() < dataLength + 4) {
            return null;//(2)
        }
        //logger.info("====================="+channel.getRemoteAddress());
        buffer.skipBytes(4);//(3)
        byte[] decoded = new byte[dataLength];
        buffer.readBytes(decoded);
        String msg = new String(decoded);//(4)
        return msg;
    }
}
