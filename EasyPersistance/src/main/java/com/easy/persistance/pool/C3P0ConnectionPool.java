package com.easy.persistance.pool;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.easy.persistance.common.DatasourceResourceBundle;
import com.easy.persistance.exception.DAORuntimeException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0ConnectionPool {
	private static DataSource dataSource = null;
	static{
		dataSource = initDataSource();
	}
	private static DataSource initDataSource() {
		String driverClassName = DatasourceResourceBundle
				.getString("database.driver");
		String url = DatasourceResourceBundle.getString("database.url");
		String username = DatasourceResourceBundle
				.getString("database.username");
		String password = DatasourceResourceBundle
				.getString("database.password");
		int initialPoolSize = Integer.parseInt(DatasourceResourceBundle
				.getString("initialPoolSize").trim());
		int maxPoolSize = Integer.parseInt(DatasourceResourceBundle
				.getString("maxPoolSize").trim());
		int minPoolSize = Integer.parseInt(DatasourceResourceBundle
				.getString("minPoolSize").trim());
		int acquireRetryDelay = Integer.parseInt(DatasourceResourceBundle
				.getString("acquireRetryDelay").trim());
		int maxIdleTime = Integer.parseInt(DatasourceResourceBundle
				.getString("maxIdleTime").trim());
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass(driverClassName);
		} catch (PropertyVetoException e) {
			throw new DAORuntimeException(e);
		}
		cpds.setUser(username);
		cpds.setPassword(password);
		cpds.setJdbcUrl(url);

		// 初始化时获取三个连接，取值应在minPoolSize与maxPoolSize之间。Default: 3 initialPoolSize
		cpds.setInitialPoolSize(initialPoolSize);
		// 连接池中保留的最大连接数。Default: 15 maxPoolSize
		cpds.setMaxPoolSize(maxPoolSize);
		// 连接池中保留的最小连接数。
		cpds.setMinPoolSize(minPoolSize);
		// 获得连接的最大等待毫秒数。Default: 1000 acquireRetryDelay
		cpds.setAcquireRetryDelay(acquireRetryDelay);
		// 最大空闲时间,60秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0 maxIdleTime
		cpds.setMaxIdleTime(maxIdleTime);

		// 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3 acquireIncrement
		// cpds.setAcquireIncrement(3);

		// 每60秒检查所有连接池中的空闲连接。Default: 0 idleConnectionTestPeriod
		// cpds.setIdleConnectionTestPeriod(60);

		// 连接关闭时默认将所有未提交的操作回滚。Default: false autoCommitOnClose
		// cpds.setAutoCommitOnClose(true);

		// JDBC的标准参数，用以控制数据源内加载的PreparedStatements数量。但由于预缓存的statements属于单个connection而不是整个连接池。所以设置这个参数需要考虑到多方面的因素。如果maxStatements与maxStatementsPerConnection均为0，则缓存被关闭。Default:
		// 0
		// cpds.setMaxStatements(1);

		// maxStatementsPerConnection定义了连接池内单个连接所拥有的最大缓存statements数
		// cpds.setMaxStatementsPerConnection(100);

		// 定义所有连接测试都执行的测试语句。在使用连接测试的情况下这个一显著提高测试速度。注意：测试的表必须在初始数据源的时候就存在。Default:
		// null preferredTestQuery
		// cpds.setPreferredTestQuery("select sysdate from dual");

		// 因性能消耗大请只在需要的时候使用它。如果设为true那么在每个connection提交的
		// 时候都将校验其有效性。建议使用idleConnectionTestPeriod或automaticTestTable
		// 等方法来提升连接测试的性能。Default: false testConnectionOnCheckout
		// cpds.setTestConnectionOnCheckout(true);

		// 如果设为true那么在取得连接的同时将校验连接的有效性。Default: false testConnectionOnCheckin
		// cpds.setTestConnectionOnCheckin(true);

		// 定义在从数据库获取新连接失败后重复尝试的次数。Default: 30 acquireRetryAttempts
		// cpds.setAcquireRetryAttempts(30);

		// 获取连接失败将会引起所有等待连接池来获取连接的线程抛出异常。但是数据源仍有效
		// 保留，并在下次调用getConnection()的时候继续尝试获取连接。如果设为true，那么在尝试
		// 获取连接失败后该数据源将申明已断开并永久关闭。Default: false breakAfterAcquireFailure
		// cpds.setBreakAfterAcquireFailure(false);
		return cpds;
	}
	public static Connection getConnection(){
		if(dataSource == null){
			dataSource = initDataSource();
		}
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new DAORuntimeException(e);
		}
	}
}
