package com.netty.udp.split.client;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public class UDPClientEncoder  extends OneToOneEncoder {
	private final int estimatedLength;

	public UDPClientEncoder() {
		this(10);
	}
	public UDPClientEncoder(int estimatedLength) {
		if (estimatedLength < 0) {
			throw new IllegalArgumentException("estimatedLength: " + estimatedLength);
		}
		this.estimatedLength = estimatedLength;
	}
    @Override
    protected Object encode(
            ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		if (msg instanceof ChannelBuffer) {
			return (ChannelBuffer) msg;
		} else {
			throw new IllegalArgumentException("msg must be a byte[], but " + msg);
		}
	
    }
}
