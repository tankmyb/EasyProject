package com.socket.nio.nioSession;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/** 
 * <p> 
 * Socket会话，记录与客户端的会话信息，每个会话对应一个客户端 
 * </p> 
 * <br> 
 *  
 * @author Ritsky 
 * 
 */  
public class SocketSession  
{     
    MultiNioServer server = null;  
      
    /**默认超时时间*/  
    private static final long DEFAULT_TIMEOUT = 30000;  
      
    /**会话ID*/  
    private int sessionId = -1;  
      
    /**Socket通道*/  
    private SocketChannel channel = null;                   //如果这个每次都是断开的?   
      
      
    /**输入buffer*/  
    private ByteBuffer inBuffer = null;  
      
    /**输出buffer*/  
    private ByteBuffer outBuffer = null;  
      
    /**输出数据列表*/  
    private List<byte[]> outDataList = Collections.synchronizedList(new ArrayList<byte[]>());   
  
    /**选择键*/  
    private SelectionKey selectionKey = null;  
      
    /**归属线程*/  
    private ReadWriteThread onwerThread = null;    //Session的归属线程   
      
    /**超时检查任务*/  
    private TimeOutCheck timeOutCheck = null;      //超时检查任务[一个定时任务]   
      
    /**超时时间*/  
    private long outTime = DEFAULT_TIMEOUT;       //超时时间   
      
    /**最近访问时间*/  
    private long lastAccessTime = System.currentTimeMillis();           //最近一次访问的时间   
      
      
  
  
    SocketSession(int _sessionId, MultiNioServer _server, SocketChannel _channel) {  
        this.sessionId = _sessionId;  
        this.channel = _channel;  
        this.server = _server;  
          
        inBuffer = ByteBuffer.allocate(Config.MOBILE_SERVER_IN_BUFFER_SIZE);  
        outBuffer = ByteBuffer.allocate(Config.MOBILE_SERVER_OUT_BUFFER_SIZE);  
    }  
      
    public long getLastAccessTime() {  
        return lastAccessTime;  
    }  
  
  
    void setLastAccessTime(long lastAccessTime) {  
        this.lastAccessTime = lastAccessTime;  
    }  
      
    public long getOutTime() {  
        return outTime;  
    }  
  
    public void setOutTime(long outTime) {  
        this.outTime = outTime;  
    }  
      
    public ReadWriteThread getOnwerThread() {  
        return onwerThread;  
    }  
  
  
    void setOnwerThread(ReadWriteThread onwerThread) {  
        this.onwerThread = onwerThread;  
    }  
      
      
    public SelectionKey getSelectionKey() {  
        return selectionKey;  
    }  
  
  
    void setSelectionKey(SelectionKey selectionKey) {  
        this.selectionKey = selectionKey;  
    }  
      
    public SocketChannel getChannel() {  
        return channel;  
    }  
  
  
  
    void setChannel(SocketChannel channel) {  
        this.channel = channel;  
    }  
      
      
      
      
    /** 
     * 启动超时检查任务 
     */  
    void startTimeOutCheckTask() {  
        if(timeOutCheck != null) {  
            timeOutCheck.cancel();  
        }  
          
        timeOutCheck = new TimeOutCheck(this);  
        server.getTimer().schedule(timeOutCheck, outTime, outTime);  
    }  
      
      
    private int handleClose()  {  
        server.removeSession((SocketSession) this);  
        LogUtil.getLogger().info("session-" + sessionId + " is going to close");  
        return 0;  
    }  
  
      
    /** 
     * 处理输入 
     * @param inBuf 
     * @return 
     */  
    public int processInput(ByteBuffer inBuf) {  
        List<NetMessage> list = server.getProtocol().parseDataInput(inBuf, this);  
        if(list == null) {   //TODO:发生错误   
            return -1;  
        }  
          
        for(int i=0; i<list.size(); i++) {  
            server.getLogicProcess().serverProcess(list.get(i));  
        }  
          
        return 0;  
    }  
      
    /** 
     * 处理读 
     * @param key 
     */  
    boolean handleRead(SelectionKey key) {  
        if(key != selectionKey){  
            LogUtil.getLogger().error("Input key not equals to th session key");  
            closeChannel();  
            this.selectionKey = key;  
            this.channel = (SocketChannel) key.channel();  
        }  
          
        int len = 0;  
          
        try{  
            len = channel.read(inBuffer);  
        }catch(IOException e){  
            LogUtil.getLogger().error(e.getMessage(), e);  
            closeChannel();  
            return false;  
        }  
          
        if(len < 0){  
            closeChannel();  
        }  
          
        int result = processInput(inBuffer);  
        if(result < 0) {  
            closeChannel();  
            return false;  
        }  
          
        this.lastAccessTime = System.currentTimeMillis();  
        return true;  
    }  
      
    /** 
     * 关闭通道 
     */  
    synchronized void closeChannel() {  
        handleClose();  
        selectionKey.cancel();  
          
        LogUtil.getLogger().info("End of connection");  
  
        try  
        {  
            channel.close();  
            channel.socket().close();  
        } catch (IOException e)  
        {}    
          
        if(this.timeOutCheck != null) {  
            this.timeOutCheck.cancel();  
        }  
        //连接关闭时   
        server.getLogicProcess().onConnectClose(this);  
    }  
  
      
      
      
    /** 
     * 发送数据 
     * @param data 
     * @return 
     */  
    public int send(byte[] data) {  
        if(data == null){  
            return -3;    
        }  
          
        if(!isOpen()) {  
            return -4;  
        }  
          
        //某些时候协议是一部分公共的头信息，比如HTTP协议，这时需要通过调用协议   
        //的打包发送数据，返回一个全新的数据   
        byte[] sendData = server.getProtocol().packDataOutput(data);  
          
        //this.outDataList.add(data);   
        this.outDataList.add(sendData);  
          
        selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);  
        selectionKey.selector().wakeup();   // cause selector.select() break   
                                                // maybe dangerous   
        return 0;  
    }  
      
      
    /** 
     * 发送网络消息 
     * @param msg 
     * @return 
     */  
    public int send(NetMessage msg) {  
        if(msg == null){  
            return -3;    
        }  
          
        if(!isOpen()) {  
            return -4;  
        }  
          
        byte[] sendData = server.getProtocol().packDataOutput(msg);  
          
        this.outDataList.add(sendData);  
          
        selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);  
        selectionKey.selector().wakeup();   // cause selector.select() break   
                                                // maybe dangerous   
        return 0;  
    }  
      
      
    /** 
     * 把输出的数据放入到输出ByteBuffer 
     */  
    private void putOutDataToByteBuffer() {  
        int size = outDataList.size();  
        for(int i=0; i<size; i++) {  
            byte[] data = outDataList.remove(0);  
            outBuffer.put(data);  
        }  
    }  
      
      
    /** 
     * 处理写,返回true成功，返回false不成功 
     * @param sk 
     * @return 
     */  
    boolean handleWrite(SelectionKey sk) {  
        //TODO:说明，最开始的做法有问题ByteBuffer不是线程安全的，所以当线程调用send时   
        //不应该直接把数据放入到outBuffer   
        putOutDataToByteBuffer();  
          
        // writing some data to network, maybe part of them    
        outBuffer.flip();  
        try{  
            channel.write(outBuffer);  
        }catch(IOException e){  
            LogUtil.getLogger().error(e.getMessage(), e);  
            closeChannel();  
            return false;  
        }  
  
        int op = SelectionKey.OP_READ;  // Tell selector to capture read   
          
        //check remain  data   
        if(outBuffer.hasRemaining()){   
            // Also add the OP_WRITE    
            op = op | SelectionKey.OP_WRITE;  
            outBuffer.compact();    // move the remaining to beginning   
        }else{  
            outBuffer.clear();      // Clean up the buffer since nothing remain   
        }  
          
          
        // Only check the read for this channel   
        sk.interestOps(op);  
          
        //this.lastAccessTime = System.currentTimeMillis();   
          
        //对于某些协议，比如HTTP连接需要在发送完数据后断开连接   
        if(server.getProtocol().isClose()) {  
            closeChannel();  
        }  
          
        return true;  
    }  
      
  
    /** 
     * 是否被打开 
     * @return 
     */  
    public synchronized boolean isOpen()  
    {  
        return channel.isOpen();  
    }  
}  

