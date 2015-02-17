package com.socket.nio.nioSession;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;

/** 
 * 读到，写入线程，管理部分会话的读写，每个会话都会有一个归属的读写线程<br> 
 * 读写线程管理着部分会话读写操作，按照原则此类是不会向使用者开发，所以<br> 
 * 很我属性就没有用到私有的，只是保证包内可见 
 *  
 * @author Ritsky 
 * 
 */  
public class ReadWriteThread extends Thread  {  
      
    /**默认注册队列大小*/  
    private static final int DEFAULT_REG_QUEUE_SIZE = 1000;  
      
    private boolean isRunning = false;  
      
    /**队列，队列可以考虑使用jdk的并发队列*/  
    private Queue<SocketSession> queue;  
      
    /**选择器*/  
    Selector selector = null;  
      
    /**归属服务者*/  
    MultiNioServer onwerServer = null;  
      
    /**读写线程id*/  
    int id = -1;  
      
    //TODO:超时连接   
    ArrayList<SocketSession> timeoutSession = new ArrayList<SocketSession>();  
      
      
  
    public ReadWriteThread(int _id, MultiNioServer _server){  
        id = _id;  
        queue = new Queue<SocketSession>(DEFAULT_REG_QUEUE_SIZE);   //TODO:这个1000的数字，应该从配置文件中获取   
          
        onwerServer = _server;  
    }  
  
    void init() throws Exception{  
        selector = Selector.open();  
    }  
  
    public void run(){  
        isRunning = true;  
  
        while(isRunning){  
            try {  
                //ADD:   
                handleTimeOutSession();  
                //END   
                register();   
                dispatch();  
  
            } catch (Exception e) {  
                LogUtil.getLogger().error(e.getMessage(), e);  
            }  
        }  
    }  
  
    /** 
     * 注册 
     */  
     private void register() {  
         for(int i=0; i<Config.REGISTER_NUM; i++) {  
             SocketSession session = null;;  
             if(queue.isEmpty()) {  
                 break;  
             }  
   
             try {  
                 session = queue.deQueue();  
             } catch (Exception e1) {  
                 LogUtil.getLogger().error(e1.getMessage(), e1);  
                 break;  
             }  
  
             if(session == null) {  
                 continue;  
             }  
  
             try {  
                 registerSession(session);  
             } catch (ClosedChannelException e) {  
                 session.getSelectionKey().cancel();  
             }  
         }  
     }  
  
     private void handleTimeOutSession() {  
         //TODO:这里不用同步的原因是，这个方法本身是处理超时后Session，   
         //而对于超时后的Session,不管在处理时加入的，还是之前加入的，反正都要进   
         //行处理，所以为了性能着想，这里不用同步，同时，可以看到我很多在ArrayList   
         //上操作remove时都是反向操作，目的是搞高处理速度，因为ArrayList在作删除   
         //操作时，如果是在中间或前面操作时，他会再次排序，并产生内存考贝操作，如   
         //果我们尽可能的靠后操作remove能减少非常多的排序与内存考贝操作   
         while(timeoutSession.size() > 0) {  
             SocketSession session = timeoutSession.remove(timeoutSession.size() - 1);  
             if(session == null) {  
                 continue;  
             }  
             session.closeChannel();   //关闭   
         }  
     }  
  
       
     private void registerSession(SocketSession session) throws ClosedChannelException   
     {  
  
         // Register the channel to handle Accept action   
         SocketChannel sc = session.getChannel();  
  
         SelectionKey key = sc.register(selector, SelectionKey.OP_READ, session);  
         session.setSelectionKey(key);  
           
         //起动超时检查任务   
         session.startTimeOutCheckTask();  
           
         this.onwerServer.sessionList.add(session);  
  
         LogUtil.getLogger().info("session is registered");  
  
     }  
  
     public void end(){  
         isRunning = false;  
     }  
  
       
     /** 
      * 分发 
      * @throws IOException 
      */  
     public void dispatch() throws IOException {  
  
         selector.select();  
  
         for(Iterator<SelectionKey> i = selector.selectedKeys().iterator(); i.hasNext();) {  
             SelectionKey sk = i.next();  
             i.remove();  
  
             if(sk.isValid() == false) {  
                 sk.cancel();  
                 continue;  
             }  
  
             if(sk.isValid() && sk.isReadable()) {  
                 handleRead(sk);  
             }  
  
             if(sk.isValid() && sk.isWritable()) {  
                 handleWrite(sk);  
             }  
         }  
     }  
  
       
     /** 
      * SocketSession发生写时执行 
      * @param session 
      */  
     private void onSocketSessionWrited(SocketSession session) {  
         Iterator<NetEventListener> iter = onwerServer.getListenerList().iterator();  
         while(iter.hasNext()) {  
             iter.next().onSocketSessionWrited(session);  
         }  
     }  
  
     /** 
      * 写处理 
      * @param sk 
      */  
     private void handleWrite(SelectionKey sk) {  
  
         LogUtil.getLogger().info("write is called");  
  
         SocketSession session = (SocketSession) sk.attachment();  
  
         if(session == null){  
             LogUtil.getLogger().error("session is null");  
             return;  
         }  
  
         if(session.handleWrite(sk)) {  
             onSocketSessionWrited(session);  
         }  
     }  
  
       
     /** 
      * SocketSession发生读时执行 
      * @param session 
      */  
     private void onSocketSessionReaded(SocketSession session) {  
         Iterator<NetEventListener> iter = onwerServer.getListenerList().iterator();  
         while(iter.hasNext()) {  
             iter.next().onSocketSessionReaded(session);  
         }  
     }  
       
  
     /** 
      * 读处理 
      * @param sk 
      */  
     private void handleRead(SelectionKey sk) {  
  
         LogUtil.getLogger().info("read is called");  
  
         SocketSession session = (SocketSession) sk.attachment();  
  
         if(session == null){  
             LogUtil.getLogger().error("session is null");  
             return;  
         }  
         if(session.handleRead(sk)) {  
             onSocketSessionReaded(session);  
         }  
     }  
  
  
     public int addSessionToQueue(SocketSession session) {  
         try  
         {  
             //调置归属线程   
             session.setOnwerThread(this);   //给出SocketSession 的归属线程   
             queue.enQueue(session);  
             selector.wakeup();  
             return 0;  
         } catch (Exception e)  
         {  
             LogUtil.getLogger().error(e.getMessage(), e);  
             return -1;  
         }  
     }   


}
