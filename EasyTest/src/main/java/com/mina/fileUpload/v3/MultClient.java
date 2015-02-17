package com.mina.fileUpload.v3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.stream.StreamIoHandler;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MultClient {
	public static void main(String args[]) throws FileNotFoundException {  
		  
        final NioSocketConnector connector = new NioSocketConnector();  
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();  
        connector.setHandler(new StreamIoHandler() {  
  
            private ExecutorService pool = Executors.newCachedThreadPool();  
  
            public void exceptionCaught(IoSession session, Throwable cause) {  
                super.exceptionCaught(session, cause);  
                System.out.println(cause);  
            }  
  
            public void sessionClosed(IoSession session) throws Exception {  
                super.sessionClosed(session);  
                System.out.println(" session close.. ");  
  
            }  
  
            public void sessionOpened(IoSession session) {  
                super.sessionOpened(session);  
                System.out.println(" session open....");  
            }  
  
            @Override  
            protected void processStreamIo(IoSession session, InputStream in, OutputStream out) {  
                pool.execute(new Worker2(session, in, out));  
  
            }  
        });  
        ConnectFuture future1 = connector.connect(new InetSocketAddress(8889));  
        future1.awaitUninterruptibly();  
        if (!future1.isConnected()) {  
            System.out.println("no connect...");  
        } else {  
            System.out.println("connected session==>start");  
        }  
//        IoSession session = future1.getSession();  
  
  
    }  
}  
  
class Worker2 extends Thread {  
  
    private BufferedInputStream in;  
    private BufferedOutputStream out;  
  
    public Worker2(IoSession session, InputStream in, OutputStream out) {  
        this.in = new BufferedInputStream(in);  
        this.out = new BufferedOutputStream(out);  
    }  
  
    public void run() {  
        System.out.println("client in process stream..");  
        FileInputStream fis = null;
        try {  
            //d:/drivers/sw8-Chipset.rar  F:\\IDE\\netbeans-6.7.1-ml-windows.exe  
            String fileName = "d:\\spring-flex-testdrive-spring3.zip";  
            File f = new File(fileName);  
            byte[] buf = new byte[2048];  
            fis = new FileInputStream(f);  
            int offset = 0;  
            while (true) {  
                //Thread.sleep(4);  
                offset = fis.read(buf);  
                if (offset == -1) {  
                    break;  
                }  
                out.write(buf, 0, offset);  
                out.flush();  
            }  
             
            //session.close(false).awaitUninterruptibly();  
            System.out.println("over..");  
  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }finally{
        	try {
				fis.close();
				out.close();
	        	in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	
        }
    }  
}
