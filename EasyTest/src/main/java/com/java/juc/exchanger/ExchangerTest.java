package com.java.juc.exchanger;

import java.util.concurrent.Exchanger;
/**
Waiter是模拟服务生的线程,首先往空杯子中添水,然后调用Exchanger的exchange方法,等待和别人交换杯子.
Customer是模拟了顾客的线程,首先把装满水的杯子喝光,然后调用Exchange的exchange方法,等待和别人交换杯子.
当服务生和顾客都准备交换杯子时,由Exchanger将服务生手中装满水的杯子和顾客手中的空杯子交换.服务生可以继续倒水,
而顾客可以继续喝水.
*/

/**

 * Exchanger让两个线程互换信息

 * 实例模拟服务生和顾客,服务生往空杯子中倒水,顾客从装满水的杯子中喝水,然后互换杯子,服务生接着倒水,顾客接着喝水.

 */

/**

 * 使用Exchanger的关键技术点如下:

 * 1.初始化Exchanger对象时,可以通过泛型指定杯子能交换的信息类型.如"new Exchanger<String>;"表示只能交换String类型的信息

 * 2.Exchanger的exchange方法表示当前线程准备交换信息,等待其他线程与它交换信息.当有其他线程调用该Exchanger对象的exchange方法时,立即交换信息

 */



public class ExchangerTest {
	// 描述一个装水的杯子

	public static class Cup {

		private boolean full = false; // 标识杯子是否有水

		public Cup(boolean full) {

			this.full = full;

		}

		// 添水,假设需要5s

		public void addWater() {

			if (!this.full) {

				try {

					Thread.sleep(5000);

				} catch (InterruptedException e) {

				}

				this.full = true;

			}

		}

		// 喝水,假设需要10s

		public void drinkWater() {

			if (this.full) {

				try {

					Thread.sleep(10000);

				} catch (InterruptedException e) {

				}

				this.full = false;

			}

		}

	}

	public static void testExchanger() {

		// 初始化一个Exchanger,并规定可交换的信息类型是杯子

		final Exchanger<Cup> exchanger = new Exchanger<Cup>();

		// 初始化一个空的杯子和装满水的杯子

		final Cup initialEmptyCup = new Cup(false);

		final Cup initialFullCup = new Cup(true);

		// 服务生线程

		class Waiter implements Runnable {

			public void run() {

				Cup currentCup = initialEmptyCup;

				try {

					int i = 0;

					while (i < 2) {

						System.out.println("服务生开始往杯子里倒水: "
								+ System.currentTimeMillis());

						// 往空的杯子里倒水

						currentCup.addWater();

						System.out.println("服务生添水完毕: "
								+ System.currentTimeMillis());

						// 杯子满后和顾客的空杯子交换

						System.out.println("服务生等待与顾客交换杯子: "
								+ System.currentTimeMillis());

						currentCup = exchanger.exchange(currentCup);

						System.out.println("服务生与顾客交换杯子完毕: "
								+ System.currentTimeMillis());

						i++;

					}

				} catch (InterruptedException ex) {

				}

			}

		}

		// 顾客线程

		class Customer implements Runnable {

			public void run() {

				Cup currentCup = initialFullCup;

				try {

					int i = 0;

					while (i < 2) {

						System.out.println("顾客开始喝水: "
								+ System.currentTimeMillis());

						// 把杯子里的水喝掉

						currentCup.drinkWater();

						System.out.println("顾客喝水完毕: "
								+ System.currentTimeMillis());

						// 将空杯子和服务生的满杯子交换

						System.out.println("顾客等待与服务生交换杯子: "
								+ System.currentTimeMillis());

						exchanger.exchange(currentCup);

						System.out.println("顾客与服务生交换杯子完毕: "
								+ System.currentTimeMillis());

						i++;

					}

				} catch (InterruptedException ex) {

				}

			}

		}

		new Thread(new Waiter()).start();

		new Thread(new Customer()).start();

	}

	public static void main(String... args) {

		ExchangerTest.testExchanger();

	}

}
