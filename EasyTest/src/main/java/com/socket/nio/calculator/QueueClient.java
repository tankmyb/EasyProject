package com.socket.nio.calculator;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import com.socket.nio.second.CharsetUtils;

public class QueueClient implements Runnable {

	private final static int BLOCK = 1024;
	private ByteBuffer r_buff1 = ByteBuffer.allocate(BLOCK);
	private ByteBuffer w_buff1 = ByteBuffer.allocate(BLOCK);
	Iterator<SelectionKey> iterator;
	SelectionKey selectionKey;
	SocketChannel client = null;
	private Selector selector;
	private SocketChannel socketChannel;

	public QueueClient(SocketChannel sc, Selector selector) {
		this.socketChannel = sc;
		this.selector = selector;
	}

	public void run() {
		Set<SelectionKey> selectionKeys = null;
		try {
			while (true) {
				if (!socketChannel.isOpen()) {
					break;
				}
				// 选择一组键，其相应的通道已为 I/O 操作准备就绪。
				// 此方法执行处于阻塞模式的选择操作。
				selector.select();
				// 返回此选择器的已选择键集。
				selectionKeys = selector.selectedKeys();
				// System.out.println(selectionKeys.size());
				iterator = selectionKeys.iterator();

				while (iterator.hasNext()) {
					selectionKey = iterator.next();
					if (selectionKey.isReadable()) {
						readHandler();
					}
					iterator.remove();
				}
				selectionKeys.clear();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// client.close();
			// sc1.close();
		}

	}

	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}

	public SocketChannel getSocketChannel() {
		return socketChannel;
	}

	public void setSocketChannel(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}

	public void readHandler() throws Exception {
		int count;
		client = (SocketChannel) selectionKey.channel();
		r_buff1.clear();
		count = client.read(r_buff1);
		r_buff1.flip();
		byte[] temp = new byte[r_buff1.limit()];
		r_buff1.get(temp);
		System.out.println("count:" + count + ",receive:"
				+ CharsetUtils.UTF8.decode(temp).toString());
		Queue.EchoToClient(CharsetUtils.UTF8.decode(temp).toString());
		// client.register(selector, SelectionKey.OP_WRITE);
		w_buff1.clear();

	}

	public void writeHandler(String echo) throws Exception {
		// client = (SocketChannel) selectionKey.channel();
		// 把回射消息放入w_buff中
		w_buff1.put(echo.getBytes());
		w_buff1.flip();
		socketChannel.write(w_buff1);
		socketChannel.register(selector, SelectionKey.OP_READ);
		r_buff1.clear();
		// client.register(selector, SelectionKey.OP_WRITE);
	}

}
