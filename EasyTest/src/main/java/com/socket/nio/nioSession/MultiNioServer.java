package com.socket.nio.nioSession;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

/** 
 * <p> 
 * NIO服务器主类，也是启动类，负责监听连接，并创建会话，与分发会话到不同的线程<br> 
 * 此类还管理着一组，会话的读写线程，按照原则此类很多方法与属性是不会向使用者开放<br> 
 * 所以为了方便，有些属性就没有设成私有，只是保证包内可见 
 * <p> 
 * <br> 
 *  
 * @author Ritsky 
 * 
 */  
public class MultiNioServer extends Thread {  
      
    /**SocketSession列表*/  
    ArrayList<SocketSession> sessionList = new ArrayList<SocketSession>();  
      
    /**读写线程组*/  
    ReadWriteThread[] rwSelector;  
      
    /**定时器*/  
    private Timer timer = new Timer(true);  
      
    //TODO:协议解析类，要放在init()通过配置文件进行初始化   
    private Protocol protocol = null;  
      
    //TODO:逻辑处理接口，启动时根据配置文件进行初始化   
    ServerLogicProcess logicProcess = null;  
  
    /**当前可用读写线程索引*/  
    int rwSelectorIdx;  
      
    /**选择器*/  
    private Selector selector = null;  
      
    /**端口*/  
    private int port = 0;  
      
      
  
    private boolean isRunning = false;  
  
    /**SocketSessionID种子*/  
    private int sessionIdSeq = 0;  
      
    private int numThread;  
      
    private ArrayList<NetEventListener> listenerList = new ArrayList<NetEventListener>();  
      
      
    ArrayList<NetEventListener> getListenerList() {  
        return listenerList;  
    }  
  
  
    public ServerLogicProcess getLogicProcess() {  
        return logicProcess;  
    }  
  
  
    public Protocol getProtocol() {  
        return protocol;  
    }  
  
  
    public MultiNioServer(int _port, int _numThread){     
        port = _port;  
        numThread = _numThread;  
        rwSelector = new ReadWriteThread[numThread];  
        for(int i=0; i<rwSelector.length; i++){  
            rwSelector[i] = new ReadWriteThread(i, this);  
            rwSelector[i].setName("ReadWrite_" + i);  
        }  
        rwSelectorIdx = 0;  
    }  
      
      
    /** 
     * 返回所有网络事件监听者列表 
     * @return 
     */  
    public List<NetEventListener> toListenerList() {  
        ArrayList<NetEventListener> list = (ArrayList<NetEventListener>) listenerList.clone();  
        return list;  
    }  
      
    /** 
     * 添加监听者 
     * @param listener 
     */  
    public void addEventListener(NetEventListener listener) {  
        listenerList.add(listener);  
    }  
      
      
    /** 
     * 移除监听者 
     * @param listener 
     * @return 
     */  
    public boolean removeListener(NetEventListener listener) {  
        return listenerList.remove(listener);  
    }  
      
      
    public int getPort() {  
        return port;  
    }  
      
    public Timer getTimer() {  
        return timer;  
    }  
  
    public int nextSelector(){  
        int ret = rwSelectorIdx;  
          
        rwSelectorIdx++;  
        if(rwSelectorIdx >= numThread){  
            rwSelectorIdx = 0;  
        }  
          
        return ret;   
    }  
      
    public ReadWriteThread getNextSelector(){  
        int idx = nextSelector();  
        if(idx < 0 || idx >= numThread){  
            return null;  
        }  
          
        return rwSelector[idx];  
    }  
      
      
    /** 
     * 初始化网络结构:   
     *  
     *  
     *  
     *  
     * @return 
     */  
    public void init() throws Exception {  
        selector = Selector.open();  
  
        // Init the working selector   
        for(int i=0; i<rwSelector.length; i++){  
            rwSelector[i].init();  
        }  
          
        // init the server socket and add register   
        ServerSocketChannel ssChannel = createServerSocketChannel(port);  
        // Register the channel to handle Accept action   
        ssChannel.register(selector, SelectionKey.OP_ACCEPT, ssChannel);  
          
        //protocol = (Protocol) Class.forName(Config.MOBILE_SERVER_PROTOCOL_CLASS).newInstance();   
          
        //logicProcess = (ServerLogicProcess) Class.forName(Config.MOBILE_SERVER_LOGIC_CLASS).newInstance();  
    }  
      
    public int startReadWriteSelector(){  
        for(int i=0; i<rwSelector.length; i++){  
            rwSelector[i].start();  
        }  
          
        return 0;  
    }  
  
    public void run(){  
        startReadWriteSelector();  
          
        isRunning = true;  
          
        while(isRunning){  
            try {  
                dispatch();  
            } catch (Exception e) {  
                LogUtil.getLogger().error(e.getMessage(), e);  
            }  
        }  
    }  
      
      
    public void end(){  
        isRunning = false;  
    }  
  
      
    /** 
     * 分发处理 
     * @throws Exception 
     */  
    public void  dispatch() throws Exception {  
          
        LogUtil.getLogger().debug("Server: Wait for incoming event");  
          
        selector.select();  
          
        for(Iterator<SelectionKey> i = selector.selectedKeys().iterator(); i.hasNext();) {  
            SelectionKey sk = i.next();  
            i.remove();  
              
            if(sk.isValid() == false) {  
                continue;  
            }  
              
            if(sk.isAcceptable()) {  
                LogUtil.getLogger().debug(System.currentTimeMillis() + ": accept sk=" + sk.hashCode());  
                handleAccept(sk);  
            }  
        }  
    }  
      
    /** 
     * SocketSession创建时触发 
     * @param session 
     */  
    private void onSocketSessionCreated(SocketSession session) {  
        Iterator<NetEventListener> iter = listenerList.iterator();  
        while(iter.hasNext()) {  
            iter.next().onSocketSessionCreated(session);  
        }  
    }  
      
  
    /** 
     * 处理接入的连接 
     * @param sk 
     * @throws Exception 
     */  
    private boolean handleAccept(SelectionKey sk) throws Exception  
    {  
        LogUtil.getLogger().info("accept is called");  
          
        ServerSocketChannel ssc = (ServerSocketChannel) sk.attachment();  
          
        if(ssc == null){  
            LogUtil.getLogger().error("ServerSocketChannel is null");  
            return false;  
        }  
          
        SocketChannel sc = ssc.accept();  
        if(sc == null)  
        {  
            LogUtil.getLogger().error("Fail to accept; SocketChannel is null");  
            return false;  
        }  
          
        sc.configureBlocking(false);  
  
        // Register the SocketChannel   
        // Warp it first   
        // Inject the customized core logic to the socketSession    
        SocketSession session = createSocketSession(sc);  
        if(session == null){  
            LogUtil.getLogger().error("Fail to create socketSession");  
            return false;  
        }  
          
        //设置Session超时时间   
        session.setOutTime(Config.MOBILE_SERVER_OUT_TIME);  
          
        //当连接打开时   
        //logicProcess.onConnectOpen(session);  
          
        ReadWriteThread thread = getNextSelector();  
        if(thread == null){  
            LogUtil.getLogger().error("Fail to get a ReadWriteThread");  
            return false;  
        }  
          
        int rc = thread.addSessionToQueue(session);  
        if(rc < 0){  
            LogUtil.getLogger().error("Fail to add session to queue");  
            return false;  
        }  
          
        onSocketSessionCreated(session);  
          
        return true;  
    }  
      
  
    /** 
     * 创建ServerSocketChannel 
     * @param port 
     * @return 
     * @throws Exception 
     */  
    private ServerSocketChannel   
                        createServerSocketChannel(int port) throws Exception{  
        ServerSocketChannel ssChannel;  
        ssChannel = ServerSocketChannel.open();  
        if(ssChannel == null){  
            return null;  
        }  
          
        // setup the channel to be non-blocking (false)   
        ssChannel.configureBlocking(false);  
  
        // Server socket and bind it   
        ServerSocket ss = ssChannel.socket();  
  
        // address = 127.0.0.1 : port  (default 127.0.0.1 if not specified)   
        InetSocketAddress address = new InetSocketAddress(port);  
        ss.bind(address);  
          
        return ssChannel;  
    }  
      
    public int nextSessionId(){  
        return ++sessionIdSeq ;  
    }  
      
    /** 
     * 创建SocketSession 
     * @param channel 
     * @return 
     */  
    private SocketSession createSocketSession(SocketChannel channel)  
    {  
        return new SocketSession(nextSessionId(), this, channel);  
    }  
      
  
      
    public synchronized SocketSession getSession(int i)  
    {     
        if(i < 0 || i >= sessionList.size()){  
            return null;  
        }  
          
        return sessionList.get(i);  
    }  
  
    public synchronized void addSession(SocketSession session)  
    {     
        sessionList.add(session);  
    }  
  
    public synchronized void removeSession(SocketSession session)  
    {     
        sessionList.remove(session);  
    }  
      
    /** 
     * 启动NIO 
     * @return 
     */  
    public static MultiNioServer startServer(){  
          
          
        //TcpRequestProcessor.init(10000, Config.WORK_THREAD);   
          
        //TODO:设定服务器的端口，与工作线程数   
        MultiNioServer server = new MultiNioServer(Config.MOBILE_SERVER_PORT, Config.MOBILE_NET_THREAD_NUM);  
          
        try {  
            server.init();  
        } catch (Exception e) {  
            LogUtil.getLogger().error(e.getMessage(), e);  
            return null;  
        }   
          
        // Starting the server thread    
        server.start();  
          
        DaemonTask dTask = new DaemonTask(server, Config.MOBILE_SERVER_DAEMON_INTERVAL);  
        dTask.start();  
          
        return server;      // 0 mean no error   
    }  
  
  
}  
