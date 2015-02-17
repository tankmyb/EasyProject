package com.java.thread.pool.v4.task;
/**
 * 任务类2
 * 执行报异常的工作任务 
 */
public class WorkTaskAImp extends WorkTaskImp{
	public WorkTaskAImp(String param) {
		super(param);
		// TODO Auto-generated constructor stub
	}
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("运行WorkTaskAImp任务时出错");
	}

}
