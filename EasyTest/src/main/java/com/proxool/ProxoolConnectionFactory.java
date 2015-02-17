package com.proxool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.admin.SnapshotIF;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;

public class ProxoolConnectionFactory {
	private static final ThreadLocal<Connection> _conn = new ThreadLocal<Connection>();
	private static ConnectionFactory connectionFactory;

	static{
		connectionFactory = ConnectionFactory.getInstance();
	}

	public static Connection getConnection()  {
		Connection conn = _conn.get();
		if (conn == null) {
			conn = connectionFactory.getConn();
			_conn.set(conn);
		}
		return conn;
	}

	public static void clearAll() throws SQLException {
		closeConn();
	}

	private static void closeConn() throws SQLException {
		Connection conn = _conn.get();
		_conn.set(null);
		if (conn != null) {
			if (!conn.isClosed()) {
				conn.close();
			}
		}
	}

	

	/**
	 * 关闭声明对象
	 * 
	 * @param psmt
	 *          声明对象
	 */
	public static void close(final PreparedStatement psmt) {
		try {
			if (psmt != null)
				psmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭返回对象
	 * 
	 * @param rs
	 *          返回结果集对象
	 */
	public static void close(final ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void monitor() throws ProxoolException {
		SnapshotIF snapshot = ProxoolFacade.getSnapshot("dbpool", true);
		int curActiveCount = snapshot.getActiveConnectionCount();// 获得活动连接数
		int availableCount = snapshot.getAvailableConnectionCount();// 获得可得到的连接数
		int maxCount = snapshot.getMaximumConnectionCount();// 获得总连接数
		System.out.println("----------------------------------");
		System.out.println(curActiveCount + "(active)  " + availableCount
				+ "(available)  " + maxCount + "(max)");
		System.out.println("----------------------------------");

	}

	public static void main(String[] args) throws ClassNotFoundException,
			IOException, SQLException, ProxoolException {
		Connection conn = ProxoolConnectionFactory.getConnection();
		System.out.println(conn);
		if (conn == null) {
			System.out.println("连接失败");
		} else {
			System.out.println("连接OK");
		}
		ProxoolConnectionFactory.clearAll();
		ProxoolConnectionFactory.monitor();
	}
}
