package com.java.juc.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 从本节开始,主要介绍J2SE5.0与线程相关的新特性,新的线程类主要集中在java.util.concurrent
 * 
 * 包中,本节实例将介绍如何使用java.util.concurrent.Callable和java.util.concurrent.Future类,
 * 
 * 用Callable定义一个任务并启动它,然后用它的Future获取输出结果并停止它.
 * 
 * 关键技术剖析:
 * 
 * 关于Callable和Future类的关键技术如下:
 * 
 * 1.Callable是类似于Runnable的接口,实现Callable接口的类和实现Runable的类都是可被其他线程
 * 
 * 执行的任务.
 * 
 * 2.Callable和Runnable的区别如下:
 * 
 * 1)Callable定义的方法是call,而Runnable定义的方法是run.
 * 
 * 2)Callable的call方法可以有返回值,而Runnable的run方法不能有返回值
 * 
 * 3)Callable的call方法可抛出异常,而Runnable的run方法不能抛出异常
 * 
 * 3.Future表示异步计算的结果,它提供了检查计算是否完成的方法,以等待计算的完成,并检索计算的
 * 
 * 结果.Future的cancel方法取消任务的执行,有一个布尔参数,参数为true表示立即中断任务的执行,参数
 * 
 * 为false表示允许正在运行的任务运行完成.Future的get方法等待计算完成,获取计算结果.
 */

public class CallableAndFuture {
	/** 自定义一个任务类,实现Callable接口 */

	public static class MyCallableClass implements Callable {

		private int flag = 0;

		public MyCallableClass(int flag) {

			this.flag = flag;

		}

		public String call() throws Exception {

			if (this.flag == 0) {

				return "flag = 0";// 如果flag的值为0,则立即返回

			}

			if (this.flag == 1) {

				// 如果flag的值为1,做一个无限循环

				try {

					while (true) {

						System.out.println("looping...");

						Thread.sleep(2000);

					}

				} catch (InterruptedException e) {

					System.out.println("Interrupted");

				}

				return "false";

			} else {

				throw new Exception("Bad flag value!");// flag不为1或0,则抛出异常

			}

		}

	}

	public static void main(String... args) {

		// 定义3个Callable类型的任务

		MyCallableClass task1 = new MyCallableClass(0);

		MyCallableClass task2 = new MyCallableClass(1);

		MyCallableClass task3 = new MyCallableClass(2);

		// 创建一个执行任务的服务

		ExecutorService es = Executors.newFixedThreadPool(3);

		try {

			// 提交并执行任务,任务启动时返回了一个Future对象

			// 如果想得到任务执行的结果或者是异常可对这个Future对象进行操作

			Future future1 = es.submit(task1);

			// 获得第一个任务的结果,如果调用get方法,当前线程会等待任务执行完毕后才往下执行

			System.out.println("task1: " + future1.get());

			Future future2 = es.submit(task2);

			// 等待5秒后,再停止第二个任务,因为第二个任务进行的是无限循环

			Thread.sleep(5000);

			System.out.println("task2 cancel: " + future2.cancel(true));

			// 获取第三个任务的输出,因为执行第三个任务会引起异常

			// 所以下面的语句将引起异常的输出

			Future future3 = es.submit(task3);

			System.out.println("task3: " + future3.get());

		} catch (Exception e) {

			System.out.println(e.toString());

		}

		es.shutdown();// 立即停止任务执行服务

	}

}
