package com.netty.udp.split.client;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

public class UDPClientDecoder extends OneToOneDecoder{

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		if (msg instanceof ChannelBuffer) {
			ChannelBuffer buffer = (ChannelBuffer) msg;
			 if (buffer.readableBytes() < 4) {
		            return null;//(1)
		        }
			 int dataLength = buffer.getInt(buffer.readerIndex());
		        if (buffer.readableBytes() < dataLength + 4) {
		            return null;//(2)
		        }
		        //logger.info("====================="+channel.getRemoteAddress());
		        buffer.skipBytes(4);//(3)
		        byte[] decoded = new byte[dataLength];
		        buffer.readBytes(decoded);
		        return new String(decoded);//(4)
		}
		return null;
	}

}
