package com.java.thread.pool.v4.test;

import com.java.thread.pool.v4.task.TaskManager;
import com.java.thread.pool.v4.task.WorkTask;
import com.java.thread.pool.v4.task.WorkTaskAImp;
import com.java.thread.pool.v4.task.WorkTaskBImp;
import com.java.thread.pool.v4.task.WorkTaskImp;
import com.java.thread.pool.v4.thread.ThreadPool;

/**
 * 线程池测试类,测试功能如下：
 * 1、测试线程池创建功能
 * 2、测试处理并发请求功能
 * 3、测试关闭功能
 **/
public class TestThreadPool {
	public static void main(String[] args){
		//创建线程池,开启处理请求服务
		final int threadCount=10;
		ThreadPool pool=ThreadPool.getInstance();
		pool.init(threadCount);
		//接收客户端请求
		WorkTask task1=new WorkTaskBImp("执行超时任务1...");
		TaskManager.addTask(task1);
		final int requestCount=20;
		for(int i=0;i<requestCount;i++){
			WorkTask task=new WorkTaskImp("执行第"+i+"个增加用户操作...");
			TaskManager.addTask(task);
		}
		WorkTask task2=new WorkTaskBImp("执行超时任务2...");
		TaskManager.addTask(task2);
		for(int i=0;i<requestCount;i++){
			WorkTask task=new WorkTaskAImp("执行第"+i+"个修改用户异常操作...");
			TaskManager.addTask(task);
		}
		for(int i=0;i<requestCount;i++){
			WorkTask task=new WorkTaskImp("执行第"+i+"个删除用户操作...");
			TaskManager.addTask(task);
		}
		//关闭线程池
		try {
			Thread.sleep(2000);//为了显示处理请求效果
			pool.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
