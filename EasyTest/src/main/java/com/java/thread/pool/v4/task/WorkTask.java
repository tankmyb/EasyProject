package com.java.thread.pool.v4.task;
/**
 * 任务接口 
 * 继承它来定义自己具体的工作任务
 */
public interface WorkTask {
	void execute() throws Exception; //执行工作任务
	void setTaskThreadKey(Object key);//设置任务线程编号
}
