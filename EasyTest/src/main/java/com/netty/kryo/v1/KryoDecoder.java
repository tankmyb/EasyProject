package com.netty.kryo.v1;



import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

import com.netty.kryo.serializer.KryoSerializer;
import com.netty.kryo.serializer.Serializer;
@Sharable
public class KryoDecoder extends OneToOneDecoder {
	private Serializer serializer = new KryoSerializer();

	@Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel,
            Object msg) throws Exception {
		//System.out.println("======================");
		if(msg instanceof ChannelBuffer){
			ChannelBuffer cb = (ChannelBuffer)msg;
			try {
				//System.out.println(System.currentTimeMillis()+"=4");
				return serializer.deserialize(cb.array());
			} catch (Exception ex) {
				throw ex;
			
		}
		}
        return null;
	}
    
}