package com.netty.protobuf.mulit;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;

import com.google.protobuf.MessageLite;

public class ProtobufCommonDecoder extends ProtobufDecoder {  
  
    public ProtobufCommonDecoder(MessageLite prototype) {  
        super(prototype);  
    }  
  
    public MessageLite invokeDecode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {  
        return (MessageLite) decode(ctx, channel, msg);  
    }  
  
}  