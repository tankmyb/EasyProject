package com.java.juc.condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**

 *有时候线程取得lock后需要在一定条件下才能做某些工作，比如经典的Producer和Consumer问题

 *在Java 5.0以前，这种功能是由Object类的wait(),notify()和notifyAll()等方法实现的

 *在5.0里面，这些功能集中到了Condition这个接口实现

 */

/**
 * 
 * 使用Condition的关键技术点如下:
 * 
 * 1.通过Lock的newCondition方法创建Condition的对象
 * 
 * 2.Condition的await方法使当前线程进入等待状态,并且释放它占据的Lock,直到有其他的线程唤醒当前线程时,重新占有Lock.
 * 
 * 3.Condition的signal方法唤醒其他正在等待该Condition的线程.
 */

public class ConditionTest {

	/**
	 * 
	 * 篮子程序，这里为了简化问题，篮子中最多只能有一个苹果
	 * 
	 * Consumer必须在篮子里有苹果的时候才能吃苹果，否则它必须暂时放弃对篮子的锁定
	 * 
	 * 等到Producer往篮子里放了苹果后再去拿来吃。
	 * 
	 * 否则它也需要暂时解锁等Comsumer把苹果吃了才能往篮子里放苹果。
	 */

	public static class Basket {

		// 锁

		Lock lock = new ReentrantLock();

		// 根据锁产生Condition对象

		Condition produced = lock.newCondition();

		Condition consumed = lock.newCondition();

		// 篮子里的苹果数，最多为1

		int num = 0;

		// 生产苹果，往篮子里放

		public void produce() throws InterruptedException {

			// 获得锁

			lock.lock();

			System.out.println("Producer get a lock...");

			try {

				// 判断是否满足生产条件

				while (num == 1) {

					// 如果有苹果，则不生产，放弃锁，进入睡眠

					// 等待消费者消费

					System.out.println("Producer sleep...");

					consumed.await();

					System.out.println("Producer awaked...");

				}

				// 生产苹果

				Thread.sleep(500);

				System.out.println("Producer produced an Apple.");

				num = 1;

				// 通知等待produced Condition的线程

				produced.signal();

			} finally {

				lock.unlock();

			}

		}

		// 消费苹果，从篮子里取

		public void consume() throws InterruptedException {

			// 获得锁

			lock.lock();

			System.out.println("Consumer get a lock...");

			try {

				// 判断是否满足消费条件

				while (num == 0) {

					// 如果没有苹果，无法消费，则放弃锁，进入睡眠

					// 等待生产者生产苹果

					System.out.println("Consumer sleep...");

					produced.await();

					System.out.println("Consumer awaked...");

				}

				// 吃苹果

				Thread.sleep(500);

				System.out.println("Consumer consumed an Apple.");

				num = 0;

				// 发信号唤醒某个等待consumed Condition的线程

				consumed.signal();

			} finally {

				lock.unlock();

			}

		}

	}

	// 测试Basket程序

	public static void testBasket() throws Exception {

		final Basket basket = new Basket();

		// 定义一个producer

		Runnable producer = new Runnable() {

			public void run() {

				try {

					basket.produce();

				} catch (InterruptedException ex) {

					ex.printStackTrace();

				}

			}

		};

		// 定义一个consumer

		Runnable consumer = new Runnable() {

			public void run() {

				try {

					basket.consume();

				} catch (InterruptedException ex) {

					ex.printStackTrace();

				}

			}

		};

		// 各生产3个consumer和producer

		ExecutorService service = Executors.newCachedThreadPool();

		for (int i = 0; i < 3; i++) {

			service.submit(producer);

		}

		for (int i = 0; i < 3; i++) {

			service.submit(consumer);

		}

		service.shutdown();

	}

	public static void main(String... args) throws Exception {

		ConditionTest.testBasket();

	}

}
