package com.socket.nio.NioServer.v2.nioserver;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * <p>Title: 主控服务线程</p>
 * @author starboy
 * @version 1.0
 */

public class Server implements Runnable {
    private static List wpool = new LinkedList();  // 回应池
    private static Selector selector;
    private ServerSocketChannel sschannel;
    private InetSocketAddress address;
    protected Notifier notifier;
    private int port;

    /**
     * 创建主控服务线程
     * @param port 服务端口
     * @throws java.lang.Exception
     */
    private static int MAX_THREADS = 4;
    public Server(int port) throws Exception {
        this.port = port;

        // 获取事件触发器
        notifier = Notifier.getNotifier();

        // 创建读写线程池
        for (int i = 0; i < MAX_THREADS; i++) {
            Thread r = new Reader();
            Thread w = new Writer();
            r.start();
            w.start();
        }

        // 创建无阻塞网络套接
        selector = Selector.open();
        sschannel = ServerSocketChannel.open();
        sschannel.configureBlocking(false);
        address = new InetSocketAddress(port);
        ServerSocket ss = sschannel.socket();
        ss.bind(address);
        sschannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void run() {
        System.out.println("Server started ...");
        System.out.println("Server listening on port: " + port);
        // 监听
        while (true) {
            try {
                int num = 0;
                num = selector.select();

                if (num > 0) {
                    Set selectedKeys = selector.selectedKeys();
                    Iterator it = selectedKeys.iterator();
                    while (it.hasNext()) {
                        SelectionKey key = (SelectionKey) it.next();
                        it.remove();
                        // 处理IO事件
                        if ( (key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                           // Accept the new connection
                           ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                           notifier.fireOnAccept();

                           SocketChannel sc = ssc.accept();
                           sc.configureBlocking(false);

                           // 触发接受连接事件
                           Request request = new Request(sc);
                           notifier.fireOnAccepted(request);

                           // 注册读操作,以进行下一步的读操作
                           sc.register(selector,  SelectionKey.OP_READ, request);
                       }
                       else if ( (key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ ) {
                           Reader.processRequest(key);  // 提交读服务线程读取客户端数据
                           key.cancel();
                       }
                       else if ( (key.readyOps() & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE ) {
                           Writer.processRequest(key);  // 提交写服务线程向客户端发送回应数据
                           key.cancel();
                       }
                    }
                }
                else {
                    addRegister();  // 在Selector中注册新的写通道
                }
            }
            catch (Exception e) {
                notifier.fireOnError("Error occured in Server: " + e.getMessage());
                continue;
            }
        }
    }

    /**
     * 添加新的通道注册
     */
    private void addRegister() {
        synchronized (wpool) {
            while (!wpool.isEmpty()) {
                SelectionKey key = (SelectionKey) wpool.remove(0);
                SocketChannel schannel = (SocketChannel)key.channel();
                try {
                    schannel.register(selector,  SelectionKey.OP_WRITE, key.attachment());
                }
                catch (Exception e) {
                    try {
                        schannel.finishConnect();
                        schannel.close();
                        schannel.socket().close();
                        notifier.fireOnClosed((Request)key.attachment());
                    }
                    catch (Exception e1) {}
                    notifier.fireOnError("Error occured in addRegister: " + e.getMessage());
                }
            }
        }
    }


    /**
     * 提交新的客户端写请求于主服务线程的回应池中
     */
    public static void processWriteRequest(SelectionKey key) {
        synchronized (wpool) {
            wpool.add(wpool.size(), key);
            wpool.notifyAll();
        }
        selector.wakeup();  // 解除selector的阻塞状态，以便注册新的通道
    }
}
