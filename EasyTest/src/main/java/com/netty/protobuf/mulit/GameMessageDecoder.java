package com.netty.protobuf.mulit;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.CorruptedFrameException;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.MessageLite;
import com.netty.protobuf.mulit.ProtobufCommonDecoder;

public class GameMessageDecoder extends OneToOneDecoder {  
  
    @Override  
    protected Object decode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
    	System.out.println("=======1");
        if (!(msg instanceof ChannelBuffer)) {  
            return msg;  
        }  
        System.out.println("=======2"); 
        ChannelBuffer buf = (ChannelBuffer) msg;  
        if( !buf.readable() ) {  
            return null;  
        }  
        System.out.println("=======3");
        buf.markReaderIndex();  
        System.out.println("==========1===messageId"+buf.readableBytes());
        //byte[] decoded = new byte[5];
        //buf.readBytes(decoded);
        final byte[] typeBuf = new byte[5];
        int messageId=-1;
        for (int i = 0; i < typeBuf.length; i ++) {
            
        	typeBuf[i] = buf.readByte();
            if (typeBuf[i] >= 0) {
                messageId = CodedInputStream.newInstance(typeBuf, 0, i + 1).readRawVarint32();
                System.out.println(messageId+"===l");
                break;
            }
        }
        		
        /*if(messageId<0){
        	buf.resetReaderIndex();
        	messageId = buf.readByte();
        }*/
        System.out.println(messageId+"=============messageId"+buf.readableBytes());
        MessageLite bodyLite = MessageMappingManager.getMessage(messageId);  
        if(bodyLite == null) {  
            buf.resetReaderIndex();  
            return null;  
        }  
          
        ProtobufCommonDecoder decoder = new ProtobufCommonDecoder(bodyLite); 
        System.out.println(decoder+"==d");
        MessageLite dataLite = decoder.invokeDecode(ctx, channel, buf);  
  
        GameMessage message = new GameMessage();  
        message.setId(messageId);  
        message.setMessage(dataLite);  
        System.out.println("====================");  
        return message;  
    }  
  
}  