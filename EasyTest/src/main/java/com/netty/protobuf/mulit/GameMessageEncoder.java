package com.netty.protobuf.mulit;

import static org.jboss.netty.buffer.ChannelBuffers.wrappedBuffer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferOutputStream;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.MessageLite;

public class GameMessageEncoder extends OneToOneEncoder {


    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel,
            Object msg) throws Exception {
    	System.out.println(msg.getClass()+"====");
    	int typeIndex = MessageMappingManager.getMessageId(msg.getClass());
    	byte[] array = null;
    	if (msg instanceof MessageLite) {
            array = ((MessageLite) msg).toByteArray();
            System.out.println("=============1");
        }
        if (msg instanceof MessageLite.Builder) {
           array = ((MessageLite.Builder) msg).build().toByteArray();
           System.out.println("=============2");
        }
        System.out.println(array+"=====array");
        ChannelBuffer body = ctx.getChannel().getConfig().getBufferFactory().getBuffer(array, 0, array.length);
        
        ChannelBuffer type =
                channel.getConfig().getBufferFactory().getBuffer(
                        body.order(),4);
        CodedOutputStream codedOutputStream1 = CodedOutputStream
                .newInstance(new ChannelBufferOutputStream(type));
        codedOutputStream1.writeRawVarint32(typeIndex);
        codedOutputStream1.flush();
        int length = body.readableBytes()+type.readableBytes();
        System.out.println(length+"=length");
        
        ChannelBuffer header =
            channel.getConfig().getBufferFactory().getBuffer(
                    body.order(),
                    CodedOutputStream.computeRawVarint32Size(length));
        
        CodedOutputStream codedOutputStream = CodedOutputStream
                .newInstance(new ChannelBufferOutputStream(header));
        codedOutputStream.writeRawVarint32(length);
        codedOutputStream.flush();
        
       
            
        return wrappedBuffer(header,type, body);
        //return wrappedBuffer(type, body);
    }


}
