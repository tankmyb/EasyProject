package com.mina.fileUpload.v3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.handler.stream.StreamIoHandler;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class Client {
	 public static void main(String args[]) throws FileNotFoundException {  
		  
	        NioSocketConnector connector = new NioSocketConnector();  
	        DefaultIoFilterChainBuilder chain = connector.getFilterChain();  
	        chain.addLast("logging", new LoggingFilter());//用于打印日志可以不写  
	        connector.setHandler(new StreamIoHandler() {  
	  
//	            private ExecutorService pool = Executors.newCachedThreadPool();  
//	            public void exceptionCaught(IoSession session, Throwable cause) {  
//	                super.exceptionCaught(session, cause);  
//	                System.out.println(cause);  
//	            }  
	  
//	            public void messageSent(IoSession session, Object message) throws Exception {  
//	                // Empty handler  
//	                super.messageSent(session, message);  
////	                System.out.println("sent===>");  
//	            }  
	//  
//	            public void messageReceived(IoSession session, Object buf) {  
//	                super.messageReceived(session, buf);  
////	                System.out.println("recv===>");  
//	            }  
	  
	            @Override  
	            protected void processStreamIo(IoSession session, InputStream in, OutputStream out) {  
	                //pool.execute(new Work(session, in, out));  
	                //System.out.println("client in process stream..");  
	                try {  
	                    String fileName = "d:\\spring-flex-testdrive-spring3.zip";  
	                    File f = new File(fileName);  
	                    byte[] buf = new byte[2048];  
	                    FileInputStream fis = new FileInputStream(f);  
	                    int offset = 0;  
	                    while (true) {  
	                        offset = fis.read(buf);  
	                        if (offset == -1) {  
	                            break;  
	                        }  
	                        System.out.println(offset);  
	                        out.write(buf, 0, offset);  
	                        //out.flush();不能flush,会阻塞，最好用线程
	                    }                
	                    //important must be waiting for over..  
	                                 
	                    fis.close();  
	                  session.close(false);       
	                    System.out.println("over..");  
	                }  catch (IOException ex) {  
	                    ex.printStackTrace();
	                }  
	            }  
	        });  
	        ConnectFuture future1 = connector.connect(new InetSocketAddress(8889));  
	        future1.awaitUninterruptibly();  
	        if (!future1.isConnected()) {  
	            System.out.println("no connect...");  
	        }  
	        IoSession session = future1.getSession();  
	        System.out.println("session==>start");  
	  
	    }  
}
