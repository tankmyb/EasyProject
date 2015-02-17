package com.proxool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.admin.SnapshotIF;

public class ProxoolMonitor {

	public static void main(String[] args) throws SQLException, ProxoolException  {
		 Connection conn =new ProxoolConnectionFactory().getConnection();
		System.out.println(conn);
		SnapshotIF snapshot = ProxoolFacade.getSnapshot("dbpool", true);
		int curActiveCount = snapshot.getActiveConnectionCount();// 获得活动连接数
    int availableCount = snapshot.getAvailableConnectionCount();// 获得可得到的连接数
    int maxCount = snapshot.getMaximumConnectionCount();// 获得总连接数

        System.out.println("----------------------------------");
        System.out
                .println(curActiveCount + "(active)  " + availableCount
                        + "(available)  " + maxCount + "(max)");
        System.out.println("----------------------------------");
    
	}
}
