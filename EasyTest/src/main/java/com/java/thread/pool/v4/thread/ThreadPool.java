package com.java.thread.pool.v4.thread;

import java.util.Vector;

import com.java.thread.pool.v4.event.BeginTaskEvent;
import com.java.thread.pool.v4.event.EndTaskEvent;
/**
 * 线程池类，功能如下：
 * 1、初始化线程池
 * 2、获取空闲线程
 * 3、任务运行，注册超时监测
 * 4、任务结束，注销超时监测
 * 5、关闭线程池
 */
public class ThreadPool {
	private int threadcount;
	private int GetIdleThreadPollTime=50;//获取空闲线程轮询间隔时间,可配置
	private static ThreadPool pool=new ThreadPool();//线程实例	
	private Vector<WorkThread> threadlist=new Vector<WorkThread>();//工作线程列表
	private TaskMonitorThread mainThread;//任务监测线程
	private TaskTimeOutThread timeThread; //任务超时线程
	private boolean StopGetIdleThread=false;
    //单例模式
	private ThreadPool(){
	}	
	public static synchronized ThreadPool getInstance(){
		return pool;
	}
	private void stopGetIdleThread(){
		StopGetIdleThread = true;
	}
	//初始化线程池
	public  void init(int count){
		System.out.println("开始初始化线程池...");
		threadcount=count;
		for(int i=0;i<count;i++){
			WorkThread t=new WorkThread(new Integer(i));
			threadlist.add(t);
			t.start();
		}
		mainThread=new  TaskMonitorThread(pool);
		mainThread.start();
		timeThread=new TaskTimeOutThread(pool);
		timeThread.start();
		System.out.println("结束初始化线程池...");
	}
	//获取空闲线程
	public  WorkThread getIdleThread(){
			while(true){
				if (StopGetIdleThread) return null;
				synchronized(threadlist){
					for(int i=0;i<threadlist.size();i++){
						WorkThread t=(WorkThread)threadlist.get(i);
						if (t.getMyState().equals(WorkThread.IDlESTATE)){
							return t;
						}
					}
				}
				try {
					Thread.sleep(GetIdleThreadPollTime);//放弃CPU,若干时间后重新获取空闲线程
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

	//任务运行，注册监测
	public  void beginTaskRun(BeginTaskEvent begin){
		timeThread.beginTaskRun(begin);
	}
	//任务结束，注销监视
	public  void endTaskRun(EndTaskEvent end){
		timeThread.endTaskRun(end);
	}
	
	//从工作线程表中移除线程
	public  void removeWorkThread(WorkThread t){
		threadlist.remove(t);
	}
	//添加新的线程
	public void addWorkThread(){
		synchronized(threadlist){
			WorkThread t=new WorkThread(new Integer(++threadcount));
			threadlist.add(t);
			t.start();
		}
	}
	
	//关闭线程池
	public  void close(){
		//停止获取空闲线程
		stopGetIdleThread();
		//关闭任务监测线程，不再接收请求
		mainThread.kill();
		//关闭超时监测线程
		timeThread.kill();
		//关闭工作线程，不再处理任务
		for(int i=0;i<threadlist.size();i++){
			WorkThread t=(WorkThread)threadlist.get(i);
			t.kill();
		}
	}
	
	
}
