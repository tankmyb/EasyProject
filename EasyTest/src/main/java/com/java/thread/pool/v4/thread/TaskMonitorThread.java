package com.java.thread.pool.v4.thread;

import com.java.thread.pool.v4.task.TaskManager;
import com.java.thread.pool.v4.task.WorkTask;
/**
 * 任务检测线程类
 * 1、自杀功能 
 */
public final class TaskMonitorThread extends Thread {
	private ThreadPool threadPool;
	private int GetWorkTaskPollTime=10;//监测任务轮询时间，可配置
	private boolean shutdown=false; 
	public TaskMonitorThread(ThreadPool pool){
		System.out.println("正在创建任务监测线程...");
		this.threadPool=pool;
	}
	private void setShutDown(boolean b){
		this.shutdown=b;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
			while(true){
				if (shutdown) break;
				WorkTask task=TaskManager.getWorkTask();//看是否有任务请求
				if (task==null){
					try {
						Thread.sleep(GetWorkTaskPollTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					WorkThread t=threadPool.getIdleThread();//获取空闲线程
					if (t==null) break;
					t.setWorkTask(task);//设置线程任务
					task.setTaskThreadKey(t.getThreadKey());//为了显示任务当前线程
					t.activate();//激活空闲线程
					try {
						Thread.sleep(GetWorkTaskPollTime); 
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	}
	//关闭线程
	public  void kill(){
		System.out.println("正在关闭任务监测线程...");
		this.setShutDown(true);
	}
	
}
