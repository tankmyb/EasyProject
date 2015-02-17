package com.java.thread.pool.v4.task;
/*
 * 任务类3
 * 执行超时的工作任务
 */
public class WorkTaskBImp extends WorkTaskImp{

	public WorkTaskBImp(String param) {
		super(param);
		// TODO Auto-generated constructor stub
	}

	public void execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("正在"+param);
	    Thread.sleep(50000);
	}

}
