package com.netty.kryo.v1;



import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.netty.kryo.serializer.KryoSerializer;
import com.netty.kryo.serializer.Serializer;
@Sharable
public class KryoEncoder extends OneToOneEncoder {
	private Serializer serializer = new KryoSerializer();
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel,
            Object msg) throws Exception {
    	//System.out.println(System.currentTimeMillis()+"=1");
    	    //byte[] b = serializer.serialize(msg);
    	    //System.out.println(b.length);
            return serializer.serialize(msg);
           
    }
}