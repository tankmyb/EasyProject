package com.java.thread.pool.v4.event;

import com.java.thread.pool.v4.task.WorkTask;
import com.java.thread.pool.v4.thread.ThreadPool;
import com.java.thread.pool.v4.thread.WorkThread;
/*
 * 任务开始运行事件
 */
public class BeginTaskEvent extends AbstractEvent{
	public BeginTaskEvent(WorkThread workthread,Thread nowthread,WorkTask task){
		this.workthread=workthread;
		this.nowthread=nowthread;
		this.nowtask=task;
	}
	@Override
	public  void execute() {
		// TODO Auto-generated method stub
		ThreadPool pool=ThreadPool.getInstance();
		pool.beginTaskRun(this);
	}
}
