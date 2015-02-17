package com.netty.file;
import java.io.File;  
import java.io.FileNotFoundException;
import java.io.FileOutputStream;  
import java.io.IOException;
  
import org.jboss.netty.buffer.ChannelBuffer;  
import org.jboss.netty.channel.ChannelHandlerContext;  
import org.jboss.netty.channel.ExceptionEvent;  
import org.jboss.netty.channel.MessageEvent;  
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;  
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;  
import org.jboss.netty.handler.codec.http.HttpChunk;  
import org.jboss.netty.handler.codec.http.HttpResponse;  
  
public class FileClientHandler extends SimpleChannelUpstreamHandler  
{  
    private volatile boolean readingChunks;  
    private File downloadFile;  
    private FileOutputStream fOutputStream = null;  
  
    @Override  
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)  
            throws Exception  
    {  
        /* 
         * 按照channle的顺序进行处理 
         * server先发送HttpResponse过来，所以这里先对HttpResponse进行处理，进行文件判断之类 
         * 之后，server发送的都是ChunkedFile了。 
         */  
          
        if (e.getMessage() instanceof HttpResponse)  
        {  
        	System.out.println("===2");
            DefaultHttpResponse httpResponse = (DefaultHttpResponse) e  
                    .getMessage();  
            String fileName = httpResponse.getHeader("fileName");
            System.out.println(fileName+"===");
            downloadFile = new File(System.getProperty("user.dir")  
                    + File.separator + "recived_" + fileName); 
            System.out.println(downloadFile);
            readingChunks = httpResponse.isChunked(); 
            if(!readingChunks){
            	write(httpResponse.getContent());
            }
            System.out.println("======"+readingChunks);
        } else  
        {  
        	 System.out.println("===1");
            HttpChunk httpChunk = (HttpChunk) e.getMessage();
            System.out.println(httpChunk+"===1");
            if (!httpChunk.isLast())  
            {  
                ChannelBuffer buffer = httpChunk.getContent();  
                if (fOutputStream == null)  
                {  
                    fOutputStream = new FileOutputStream(downloadFile);  
                }  
                while (buffer.readable())  
                {  
                    byte[] dst = new byte[buffer.readableBytes()];  
                    buffer.readBytes(dst);  
                    fOutputStream.write(dst);  
                }  
            } else  
            {  
                readingChunks = false;  
            }  
            fOutputStream.flush();  
        }  
        if (!readingChunks)  
        {  
            fOutputStream.close();  
            e.getChannel().close();  
        }  
    }  
    private void write(ChannelBuffer buffer) throws IOException{
     
           if (fOutputStream == null)  
           {  
               fOutputStream = new FileOutputStream(downloadFile);  
           }  
           while (buffer.readable())  
           {  
               byte[] dst = new byte[buffer.readableBytes()];  
               buffer.readBytes(dst);  
               fOutputStream.write(dst);  
           }  
      
       fOutputStream.flush();  
   
    }
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)  
            throws Exception  
    {  
        System.out.println(e.getCause());  
    }  
} 
