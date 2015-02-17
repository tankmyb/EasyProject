package com.mina.myDecoder.v2;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class MyProtocalEncoder extends ProtocolEncoderAdapter {  
    private final Charset charset;  
    public MyProtocalEncoder(Charset charset) {  
        this.charset = charset;  
    }  
    //在此处实现对MyProtocalPack包的编码工作，并把它写入输出流中  
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {  
        MyProtocalPack value = (MyProtocalPack) message;  
        IoBuffer buf = IoBuffer.allocate(value.getLength());  
        buf.setAutoExpand(true);  
        buf.putInt(value.getLength());  
        buf.put(value.getFlag());  
        if (value.getContent() != null)  
            buf.put(value.getContent().getBytes());  
        buf.flip();  
        out.write(buf);  
    }  
    public void dispose() throws Exception {  
    }  

}
