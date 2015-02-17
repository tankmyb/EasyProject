package com.java.thread.pool.v4.thread;

import com.java.thread.pool.v4.event.BeginTaskEvent;
import com.java.thread.pool.v4.event.EndTaskEvent;
import com.java.thread.pool.v4.task.WorkTask;
/**
 * 工作线程类,功能如下：
 * 1、执行业务方法，业务参数可动态设置
 * 2、自身状态可设置、可获取
 * 3、自我唤醒功能
 * 4、自杀功能
 */
public final class WorkThread extends Thread{
	private boolean shutdown=false;
	private String info; //业务参数
	private Object threadKey;//线程标识
	private Object lock=new Object();//锁对象
	private String state; //线程状态
	private int waitExecFinishPollTime=500;//关闭线程时的轮询等待时间，可配置
	public static final String CREATESTATE="1";//创建状态
	public static final String RUNSTATE="2";   //运行状态
	public static final String IDlESTATE="3";  //空闲状态
    private WorkTask nowTask; //当前任务

	//获取线程标识key
	public Object getThreadKey() {
		return threadKey;
	}      
    //设置线程的任务
    public void setWorkTask(WorkTask task){
    	this.nowTask=task;
    }
	//设置是否关闭线程
	private void setShutdown(boolean shutdown) {
		this.shutdown = shutdown;
	}
	//设置线程状态
	private void setMyState(String state){
		this.state=state;
	}
	//获取线程状态
	public String getMyState(){
		return state;
	}
	public WorkThread(Object key){
		System.out.println("正在创建工作线程...线程编号"+key.toString());
		this.threadKey=key;
		this.state=CREATESTATE;
	}
	
	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		super.start();
		setMyState(RUNSTATE);
	}
	public void run(){
		while(true){
			try {
				setMyState(IDlESTATE);
				synchronized(this){
					wait(); /*开始等待，直至被激活*/
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (shutdown) break;
			try{
				new BeginTaskEvent(this,Thread.currentThread(),nowTask).execute();
				nowTask.execute();//执行业务
				new EndTaskEvent(this,Thread.currentThread(),nowTask).execute();
			}catch(Exception e){
				new EndTaskEvent(this,Thread.currentThread(),nowTask).execute();
				System.out.println(e.getMessage());
			}
		}
	}
	//重新激活线程
	public void activate(){
		synchronized(this){
			setMyState(RUNSTATE);
			notify();
		}
	}
	//关闭线程
	public void kill(){
		synchronized(this){
		    if (this.getMyState().equals(IDlESTATE)){//如果线程处于空闲状态,则直接关掉
				System.out.println("正在关闭工作线程...线程编号"+threadKey.toString());
		    	this.setShutdown(true);
		    	this.activate();
		    }else if (this.getMyState().equals(RUNSTATE)){//如果线程处于运行状态,则执行完后再关掉
				System.out.println("正在等待线程执行业务完成...工作线程编号"+threadKey.toString());
		    	while(this.getMyState().equals(RUNSTATE)){
		    		try {
						Thread.sleep(waitExecFinishPollTime);//放弃CPU,若干时间后再检查线程状态
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
				System.out.println("正在关闭工作线程...线程编号"+threadKey.toString());
		    	this.setShutdown(true);
		    	this.activate();
		    }
		}
	}
}

