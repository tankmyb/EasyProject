package com.java.jdbc.dbutils;

import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class JdbcUtils {
	private static DataSource dataSource;
	 static{
	  try
	  {
	   InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
	   Properties prop = new Properties();
	   prop.load(in);

	   BasicDataSourceFactory factory = new BasicDataSourceFactory();
	   dataSource = factory.createDataSource(prop); 
	  }
	  catch(Exception e)
	  {
	   throw new ExceptionInInitializerError(e);
	  }
	 } 
}
