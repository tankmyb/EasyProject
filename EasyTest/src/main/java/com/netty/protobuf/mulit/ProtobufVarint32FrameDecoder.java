package com.netty.protobuf.mulit;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.CorruptedFrameException;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.google.protobuf.CodedInputStream;

public class ProtobufVarint32FrameDecoder extends FrameDecoder {

    // TODO maxFrameLength + safe skip + fail-fast option
    //      (just like LengthFieldBasedFrameDecoder)

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        buffer.markReaderIndex();
        final byte[] buf = new byte[5];
        for (int i = 0; i < buf.length; i ++) {
            if (!buffer.readable()) {
                buffer.resetReaderIndex();
                return null;
            }

            buf[i] = buffer.readByte();
            if (buf[i] >= 0) {
                int length = CodedInputStream.newInstance(buf, 0, i + 1).readRawVarint32();
                System.out.println(length+"===l");
                if (length < 0) {
                    throw new CorruptedFrameException("negative length: " + length);
                }
                System.out.println(buffer.readableBytes()+"===");
                if (buffer.readableBytes() < length) {
                    buffer.resetReaderIndex();
                    return null;
                } else {
                    return buffer.readBytes(length);
                }
            }
        }

        // Couldn't find the byte whose MSB is off.
        throw new CorruptedFrameException("length wider than 32-bit");
    }
}

