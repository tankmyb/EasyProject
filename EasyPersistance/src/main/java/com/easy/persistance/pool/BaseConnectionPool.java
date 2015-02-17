package com.easy.persistance.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.easy.persistance.common.DatasourceResourceBundle;
import com.easy.persistance.exception.DAORuntimeException;

public class BaseConnectionPool {

	public static Connection getConnection() {
		Connection conn = null;
		String driver = DatasourceResourceBundle.getString("database.driver");
		String url = DatasourceResourceBundle.getString("database.url");
		String username = DatasourceResourceBundle.getString(
				"database.username");
		String password = DatasourceResourceBundle.getString(
				"database.password");
		try {
			Class.forName(driver).newInstance(); // 加载驱动
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			throw new DAORuntimeException("驱动不存在", e);
		} catch (InstantiationException e) {
			throw new DAORuntimeException("不能实例化", e);
		} catch (IllegalAccessException e) {
			throw new DAORuntimeException(e);
		} catch (SQLException e) {
			throw new DAORuntimeException(e);
		}
		return conn;
	}
}
