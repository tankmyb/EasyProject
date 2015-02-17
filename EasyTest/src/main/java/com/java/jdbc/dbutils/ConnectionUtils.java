package com.java.jdbc.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

public class ConnectionUtils {

public static Connection getConnection() {
	  Connection conn = null;
	  String jdbcURL="jdbc:mysql://192.168.4.22:3306/box?useUnicode=true&characterEncoding=utf8&autoReconnect=true";
	  String user = "root";
	  String password ="nihaotv";  
	  try {
	   DbUtils.loadDriver("com.mysql.jdbc.Driver");
	   conn = DriverManager.getConnection(jdbcURL,user, password);   
	  } catch (SQLException e) {
	   e.printStackTrace();
	  }   
	  return conn;
	}

}
