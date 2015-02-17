package com.netty.udp.split.server;

import java.util.ArrayList;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;


public class UDPDecoder extends OneToOneDecoder{

	private List<byte[]> list = new ArrayList<byte[]>();
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		if (msg instanceof ChannelBuffer) {
			ChannelBuffer buffer = (ChannelBuffer) msg;
			 if (buffer.readableBytes() < 4) {
		            return null;//(1)
		        }
			 int dataLength = buffer.getInt(buffer.readerIndex());
			 if(dataLength==0){
				 buffer.skipBytes(4);
				 int seq = buffer.getInt(buffer.readerIndex());
				 System.out.println(seq);
				 buffer.skipBytes(4);
				 int length = buffer.getInt(buffer.readerIndex());
				// System.out.println(length);
				 buffer.skipBytes(4);
				 byte[] decoded = new byte[length];
			        buffer.readBytes(decoded);
			        
			        return new String(decoded);//(4)
			 }else {
				 if (buffer.readableBytes() < dataLength + 4) {
			            return null;//(2)
			        }
			        //logger.info("====================="+channel.getRemoteAddress());
			        buffer.skipBytes(4);//(3)
			        byte[] decoded = new byte[dataLength];
			        buffer.readBytes(decoded);
			        
			        return new String(decoded);//(4)
			 }
			 //System.out.println(dataLength+"====="+buffer.readableBytes());
		        
		}
		return null;
	}

}
