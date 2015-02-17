package com.proxool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.logicalcobwebs.proxool.ProxoolException;

public class Test {

	private static final Integer len = 1000;
	
	public static void main(String[] args) throws SQLException, ProxoolException {
		CountDownLatch begin = new CountDownLatch(1);
		CountDownLatch end = new CountDownLatch(len);
		Long start = System.currentTimeMillis();
		ExecutorService exe = Executors.newFixedThreadPool(20);
		for(int i=0;i<len;i++){
			exe.execute(new ConnectionThread(begin,end));
		}
		System.out.println("begin");
		begin.countDown();// 宣布开始
		try {
			end.await();// 等待结束
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("end");
		}
		System.err.println(System.currentTimeMillis() - start);
		ProxoolConnectionFactory.monitor();
		exe.shutdown();
		//ProxoolConnectionFactory.monitor();
	}
	static class ConnectionThread implements Runnable{
		private CountDownLatch begin;

		private CountDownLatch end;
		private Connection connection =ProxoolConnectionFactory.getConnection();
		public ConnectionThread(CountDownLatch begin,CountDownLatch end){
			this.begin = begin;
			this.end = end;
		}
		public void run() {
			try{
				begin.await();
				for(int i=0;i<100;i++){
					PreparedStatement stmt=connection.prepareStatement("select * from endw_appattr");
					ResultSet rs =stmt.executeQuery();
					//if(rs.next()){
						//System.out.println(rs.getString(1));
					//}
					ProxoolConnectionFactory.close(rs);
					ProxoolConnectionFactory.close(stmt);
					
				}
				ProxoolConnectionFactory.clearAll();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			end.countDown();// 向评委报告跑到终点了
		}
	}
}
