package com.java.juc.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
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

public class CallableAndFuture1 {
	/** 自定义一个任务类,实现Callable接口 
	 * @throws ExecutionException 
	 * @throws InterruptedException */

	

	public static void main(String... args) throws InterruptedException, ExecutionException {

		

		ExecutorService es = Executors.newFixedThreadPool(3);


			// 提交并执行任务,任务启动时返回了一个Future对象

			// 如果想得到任务执行的结果或者是异常可对这个Future对象进行操作

			Future<String> future1 = es.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					
					return "aaaa";
				}
			});

		//es.shutdown();// 立即停止任务执行服务
		future1 = es.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				
				return "aaaa";
			}
		});
		System.out.println(future1.get());
	}

}
