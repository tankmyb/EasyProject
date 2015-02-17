package com.java.thread.pool.v4.event;
/*
 * 任务运行时间类
 */
public class TaskRunTime {
		private long begintime;
		private long endtime;
		private BeginTaskEvent event;
		public TaskRunTime(BeginTaskEvent event){
			this.event=event;
			this.begintime=System.currentTimeMillis();
			this.endtime=this.begintime;
		}
		public BeginTaskEvent getEvent() {
			return event;
		}
		//检查是否超时
		public boolean checkRunTimeOut(long maxtime){
			endtime=System.currentTimeMillis();
			long cha=endtime-begintime;
			return cha>=maxtime;
		}
		
	}
