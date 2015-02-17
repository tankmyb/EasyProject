package com.proxool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;

public class ConnectionFactory {

	private static ConnectionFactory connectionFactory = null;

	private ConnectionFactory() {
		init();
	}

	public static ConnectionFactory getInstance() {
		if (connectionFactory == null) {
			connectionFactory = new ConnectionFactory();
		}
		return connectionFactory;
	}

	private void init() {
		Properties dbProps = new Properties();
		try {
			dbProps.load(ProxoolConnectionFactory.class
					.getResourceAsStream("proxool.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			PropertyConfigurator.configure(dbProps);
		} catch (ProxoolException e) {
			e.printStackTrace();
		}
	}
	public Connection getConn(){
		Connection conn = null;
		try{
			conn = DriverManager.getConnection("proxool.dbpool");//这句是重点，这样就能获取到连接所需的connect对象了。
		}catch(Exception e){
			e.printStackTrace();
		}
		   
		return  conn;
	}
}
