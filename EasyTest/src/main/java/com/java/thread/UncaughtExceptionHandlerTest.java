package com.java.thread;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Thread的run方法是不抛出任何检查型异常(checked
 * exception)的,但是它自身却可能因为一个异常而被终止，导致这个线程的终结。最麻烦的是
 * ，在线程中抛出的异常即使在主线程中使用try...catch也无法截获
 * ，因此可能导致一些问题出现，比如异常的时候无法回收一些系统资源，或者没有关闭当前的连接等等。
 * 
 * 主线程之所以不处理子线程抛出的RuntimeException,是因为线程是异步的，子线程没结束，主线程可能已经结束了。
 * 
 * UncaughtExceptionHandler名字意味着处理未捕获的异常。更明确的说，它处理未捕获的运行时异常。Java编译器要求处理所有非运行时异常，
 * 否则程序不能编译通过。这里“处理”的是方法里throws子句声明的异常或在try-catch块里的catch子句的异常。
 * 
 * @author Administrator
 * 
 */
public class UncaughtExceptionHandlerTest {
	public static void main(String[] args) {
		ThreadA a = null;
		try {
			ErrHandler handle = new ErrHandler();
			a = new ThreadA();
			a.setUncaughtExceptionHandler(handle);// 加入定义的ErrHandler
			a.start(); // 线程的run抛出的RuntimeException异常无法抓到
			// a.run(); 普通方法抛出RuntimeException异常可以抓到
		} catch (Exception e) {
			System.out.println("catch RunTimeException a"); // 不起作用，但是Exception已经交给handle处理
		}

		// 普通线程即使使用try...catch也无法捕获到抛出的异常
		try {
			ThreadB b = new ThreadB();
			b.start();
		} catch (Exception e) {
			System.out.println("catch RunTimeException b"); // 不起作用
		}
	}

}

/**
 * 自定义的一个UncaughtExceptionHandler
 */
class ErrHandler implements UncaughtExceptionHandler {
	/**
	 * 这里可以做任何针对异常的处理,比如记录日志等等
	 */
	public void uncaughtException(Thread a, Throwable e) {
		System.out.println("This is:" + a.getName() + ",Message:" + e.getMessage());
		e.printStackTrace();
	}
}

/**
 * 拥有UncaughtExceptionHandler的线程
 */
class ThreadA extends Thread {
	public ThreadA() {
	}

	public void run() {
		double i = 12 / 0;// 抛出ArithmeticException的RuntimeException型异常
	}
}

/**
 * 普通线程
 */
class ThreadB extends Thread {
	public ThreadB() {
	}

	public void run() {
		try {
			double i = 12 / 0;// 抛出ArithmeticException的RuntimeException型异常
		} catch (RuntimeException e) {
			throw e;
		}
	}
}
