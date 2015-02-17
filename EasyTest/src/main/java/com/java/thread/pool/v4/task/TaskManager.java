package com.java.thread.pool.v4.task;

import java.util.ArrayList;
import java.util.List;
/**
 *  任务管理器
 *  1、添加任务
 *  2、监测是否有新任务
 */
public class TaskManager {
	private  static List<WorkTask> taskQueue=new ArrayList<WorkTask>(); //任务队列 
	private TaskManager(){
		
	}
	//添加任务                                            
	public synchronized static  void addTask(WorkTask task){
		taskQueue.add(task);
	}
	//判断是否有任务未执行
	public synchronized static WorkTask getWorkTask(){
		if (taskQueue.size()>0){
			return (WorkTask)taskQueue.remove(0);
		}else
			return null;
	}
}
