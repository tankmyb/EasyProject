package com.socket.nio.calculator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.socket.nio.second.CharsetUtils;

public class Queue implements Runnable {
	public static final int BLOCK = 1024;
	private static ByteBuffer r_buff = ByteBuffer.allocate(BLOCK);
	private static ByteBuffer w_buff = ByteBuffer.allocate(BLOCK);
	private static int PORT = 8850;
	private static SocketChannel sc = null;
	private static Map<Integer, QueueClient> map = new HashMap<Integer, QueueClient>();

	public Queue() {
		new Thread(this).start();
	}

	public void run() {
		try {
			// 生成一个侦听端
			ServerSocketChannel ssc = ServerSocketChannel.open();
			// 将侦听端设为异步方式
			ssc.configureBlocking(false);
			// 生成一个信号监视器
			Selector s = Selector.open();
			// 侦听端绑定到一个端口
			ssc.socket().bind(new InetSocketAddress(PORT));
			// 设置侦听端所选的异步信号OP_ACCEPT
			ssc.register(s, SelectionKey.OP_ACCEPT);
			System.out.println("queue has been set up ......" + PORT);

			while (true) {
				int n = s.select();
				if (n == 0) {// 没有指定的I/O事件发生
					continue;
				}
				Iterator<SelectionKey> it = s.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey key = it.next();
					if (key.isAcceptable()) {// 侦听端信号触发
						ServerSocketChannel server = (ServerSocketChannel) key
								.channel();
						// 接受一个新的连接
						sc = server.accept();
						sc.configureBlocking(false);
						// 设置该socket的异步信号OP_READ:当socket可读时，

						sc.register(s, SelectionKey.OP_READ);
					} else if (key.isReadable()) {// 某socket可读信号
						System.out.println("来了");
						dealwithData(key);
					}
					it.remove();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dealwithData(SelectionKey key) throws IOException {
		String receive = null;
		int count;
		// 由key获取指定socketchannel的引用
		SocketChannel sc = (SocketChannel) key.channel();
		r_buff.clear();
		w_buff.clear();
		boolean flag = false;
		// 读取数据到r_buff
		while ((count = sc.read(r_buff)) > 0) {
			// 确保r_buff可读
			r_buff.flip();
			// 将数据返回给客户端
			receive = CharsetUtils.UTF8.decode(r_buff).toString();
			System.out.println("count:" + count + ",收到：" + receive);
		}
		if (!receive.equals("end")) {
			String[] str = receive.split(":");
			int port = Integer.parseInt(str[0]);
			try {
				SocketChannel sc1 = null;
				Selector selector = null;
				QueueClient c = null;
				if (map.containsKey(port)) {
					c = map.get(port);
					selector = c.getSelector();
					sc1 = c.getSocketChannel();
				} else {
					InetSocketAddress addr = new InetSocketAddress("127.0.0.1",
							port);
					// 生成一个socketchannel
					sc1 = SocketChannel.open();

					sc1.configureBlocking(false);
					// 打开选择器
					selector = Selector.open();
					// 注册连接服务端socket动作
					sc1.register(selector, SelectionKey.OP_CONNECT);
					// 连接
					sc1.connect(addr);
					while (!sc1.finishConnect()) {
			        }
					c = new QueueClient(sc1, selector);
					map.put(port, c);
					new Thread(c).start();
					//Thread.sleep(1000L);
				}
				map.get(port).writeHandler(str[1]);
				// sc1.register(selector, SelectionKey.OP_WRITE);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		w_buff.clear();
		r_buff.clear();
		if (flag) {
			sc.close();
		}
	}

	public static void EchoToClient(String message) throws IOException {
		w_buff.put(message.toString().getBytes());
		w_buff.flip();
		while (w_buff.hasRemaining()) {
			sc.write(w_buff);
		}
	}

	public static void main(String[] args) {
		new Queue();
	}
}
