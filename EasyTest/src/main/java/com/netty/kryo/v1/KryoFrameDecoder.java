package com.netty.kryo.v1;


import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
@Sharable
public class KryoFrameDecoder extends FrameDecoder {

    // TODO maxFrameLength + safe skip + fail-fast option
    //      (just like LengthFieldBasedFrameDecoder)

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer in) throws Exception {
    	//System.out.println("==============");
		if(in.readableBytes()<4){
			return null;
		}
		in.markReaderIndex();
		int length = in.readInt();
		//System.out.println(length+"============lenth");
		if (in.readableBytes() < length) {
			in.resetReaderIndex();
		} else {
			//System.out.println(System.currentTimeMillis()+"=3");
			return in.readBytes(length);
		}
		return null;
	}
}
