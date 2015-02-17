package com.netty.kryo.v1;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;


@Sharable
public class KryoFrameEncoder extends  ObjectEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		if (msg instanceof byte[]) {
			//System.out.println(System.currentTimeMillis()+"=2");
			byte[] bytes = (byte[]) msg;
			ChannelBuffer ob = ChannelBuffers.dynamicBuffer(512, channel.getConfig().getBufferFactory());
			ob.writeInt(bytes.length);
			ob.writeBytes(bytes);
            //System.out.println("================="+bytes.length);
			return ob;
		} else {
			throw new IllegalArgumentException("msg must be a byte[], but " + msg);
		}
	}
}