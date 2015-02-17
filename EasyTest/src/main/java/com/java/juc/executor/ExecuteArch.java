package com.java.juc.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 在J2SE之前启动一个任务是通过调用Thread类的start方法来实现的,任务的提交和执行是同时进行的,如果想对任务的执行进行调度,
 * 或是控制同时执行的线程数量就需要额外的编写代码来完成.
 * 
 * J2SE5.0提供了一个新的任务执行架构,可以轻松地高度和控制任务的执行,并且可以建立一个线程池来执行任务.
 * 
 * 实例介绍如何使用新的任务执行架构,运行Runnable和Callable任务,包括定时执行任务,按规律执行任务和停止任务.
 * 
 * 关键技术剖析:
 * 
 * 使用新的任务执行框架的关键技术如下:
 * 
 * 1.Executor服务对象是用来执行Runnable任务的,常用的方法如下:
 * 
 * execute方法用于执行Runnable类型的任务.
 * 
 * 2.ExecutorService服务对象能执行和终止Callable任务,它继承了Executor,所以也能执行Runnable任务.常用的方法如下
 * 
 * a) submit方法用来提交Callable或Runnable任务,并返回代表此任务的Future对象.
 * 
 * b) invokeAll方法批处理任务集合,并返回一个代表这些任务的Future对象集合
 * 
 * c) shutdown方法在完成自己已提交的任务后关闭服务,不再接受新任务.
 * 
 * d) shutdownNow方法停止所有正在执行的任务并关闭服务.
 * 
 * e) isTerminated测试是否所有任务都执行完毕了
 * 
 * g) isShutdown测试是否该ExecutorService已被关闭
 * 
 * 3.ScheduledExecutorService服务对象继承ExecutorService,提供了按时间安排执行任务的功能.常用的方法如下:
 * 
 * a)schedule(task,initDelay)方法安排所提交的Callable或Runnable任务在initDelay指定的时间后执行.
 * 
 * b)scheduleAtFixedRate方法安排所提交的Runnable任务按指定的间隔重复执行.
 * 
 * c)scheduleWithFixedDelay方法安排所提交的Runnable任务在每次执行完后,等待delay所指定的时间后重复执行.
 * 
 * 4.Executors类用来创建各种服务对象,常用的方法如下:
 * 
 * a)callable(Runnable task)方法将Runnable的任务转化成Callable的任务.
 * 
 * b)newSingleThreadExecutor方法产生一个ExecutorService对象,这个对象带有一个线程池,线程池的大小会根据需要调整,
 * 线程执行完任务后返回线程池,供执行下一次任务使用.
 * 
 * c)newCachedThreadPool方法会产生一个ExecutorService对象,这个对象带有一个线程池,线程池的大小会根据需要调整,
 * 线程执行完任务后返回线程池,供执行下一次任务使用.
 * 
 * d)newFixedThreadPool(int
 * poolSize)方法产生一个ExecutorService对象,这个对象带有一个大小为poolSize的线程池
 * ,若任务数量大于poolSize,任务会被放在一个队列里顺序执行.
 * 
 * e)newSingleThreadScheduledExecutor方法产生一个ScheduledExecutorService对象,
 * 这个对象的线程池大小为1,若任务多于一个,任务将按先后顺序执行.
 * 
 * f)newScheduledThreadPool(int
 * poolSize)方法产生一个ScheduledExecutorService对象,这个对象的线程池大小为poolSize
 * ,若任务数量大于poolSize,任务会在一个队列里等待执行.
 */

public class ExecuteArch {
	/** 该线程输出一行字符串 */

	public static class MyThread implements Runnable {

		public void run() {

			System.out.println("Task repeating. " + System.currentTimeMillis());

			try {

				Thread.sleep(1000);

			} catch (InterruptedException e) {

				System.out.println("Task interrupted. "
						+ System.currentTimeMillis());

			}

		}

	}

	/** 该Callable结束另一个任务 */

	public static class MyCallable implements Callable {

		private Future future;

		public MyCallable(Future future) {

			this.future = future;

		}

		public String call() {

			System.out.println("To cancell Task..."
					+ System.currentTimeMillis());

			this.future.cancel(true);

			return "Task cancelled!";

		}

	}

	public static void main(String... args) throws InterruptedException,
			ExecutionException {

		// 产生一个ExcutorService对象,这个对象带有一个线程池,线程池的大小会根据需要调整

		// 线程执行完任务后返回线程池,供执行下一次任务使用

		ExecutorService cachedService = Executors.newCachedThreadPool();

		Future myThreadFuture = cachedService.submit(new MyThread());

		Future myCallableFuture = cachedService.submit(new MyCallable(
				myThreadFuture));

		System.out.println(myCallableFuture.get());

		System.out.println("--------------------");

		// 将Runnable任务转换成 Callable任务

		Callable myThreadCallable = Executors.callable(new MyThread());

		Future myThreadCallableFuture = cachedService.submit(myThreadCallable);

		// 对于Runnable任务,转换成Callable任务后,也没有返回值

		System.out.println(myThreadCallableFuture.get());

		cachedService.shutdownNow();

		System.out.println("--------------------");

		// 产生一个ExecutorService对象,这个对象带有一个大小为poolSize的线程池

		// 若任务大于poolSize,任务会被放在一个queue里顺序执行

		ExecutorService fixedService = Executors.newFixedThreadPool(2);

		fixedService.submit(new MyThread());

		fixedService.submit(new MyThread());

		// 由于线程池大小为2,所以后面的任务必须等待前面的任务执行完毕后才能被执行

		myThreadFuture = fixedService.submit(new MyThread());

		myThreadFuture = fixedService.submit(new MyCallable(myThreadFuture));

		System.out.println(myCallableFuture.get());

		fixedService.shutdown();

		System.out.println("--------------------");

		// 产生一个ScheduleExecutorService对象,这个对象的线程池大小为poolSize

		// 若任务数量大于poolSize,任务会在一个queue里等待执行

		ScheduledExecutorService fixedScheduledService = Executors
				.newScheduledThreadPool(2);

		MyThread task1 = new MyThread();

		// 使用任务执行服务立即执行任务1,而且此后每隔2秒执行一次任务1

		myThreadFuture = fixedScheduledService.scheduleAtFixedRate(task1, 0, 2,
				TimeUnit.SECONDS);

		MyCallable task2 = new MyCallable(myThreadFuture);

		// 使用任务执行服务等待5秒后执行任务2,执行它后将任务1关闭.

		myCallableFuture = fixedScheduledService.schedule(task2, 5,
				TimeUnit.SECONDS);

		System.out.println(myCallableFuture.get());

		fixedScheduledService.shutdownNow();

	}

}
