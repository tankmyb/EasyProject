package com.easy.persistance.common;

import java.sql.Connection;
import java.sql.SQLException;

import com.easy.persistance.exception.DAORuntimeException;
import com.easy.persistance.pool.BaseConnectionPool;
import com.easy.persistance.pool.C3P0ConnectionPool;

public class ConnectionFactory {

	private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();;

	private static Connection getConnection() {
		Connection conn = null;
		String connectionPoolName = DatasourceResourceBundle
				.getString("connection.pool");
		if (connectionPoolName.equalsIgnoreCase(SQLAssistant.C3p0PoolName)) {
			conn = C3P0ConnectionPool.getConnection();
		} else {
			conn = BaseConnectionPool.getConnection();
		}
		SQLAssistant.getDatabaseNameAndVersion(conn);
		return conn;
	}

	public static Connection currentConnection() {
		Connection connection = connectionThreadLocal.get();
		if (connection == null) {
			connection = getConnection();
			connectionThreadLocal.set(connection);
		}
		return connection;
	}

	public static void closeConnection() {
		Connection connection = connectionThreadLocal.get();
		if (connection != null) {
			connectionThreadLocal.set(null);
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DAORuntimeException("connection关闭失败", e);
				}
				connection = null;
			}
		}
	}

}
