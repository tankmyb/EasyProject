package com.java.jdbc.dbutils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
@SuppressWarnings("unchecked")
public class BaseDAO<T> {
	protected Class<T> entityClass;

	public BaseDAO() {
	  this.entityClass = getSuperClassGenricType(getClass(),0);
	  // getSuperClassGenricType(clazz, index)
	}


	public BaseDAO(Class clazz) {
	  this.entityClass = clazz;
	}


	public Connection getConnection() {
	  
	  Connection conn = null;
	  String jdbcURL="jdbc:mysql://127.0.0.1:3306/cfs_tmp?useUnicode=true&characterEncoding=utf8&autoReconnect=true";
	  String user = "root";
	  String password ="123456";  
	  try {
	   DbUtils.loadDriver("com.mysql.jdbc.Driver");
	   conn = DriverManager.getConnection(jdbcURL,user, password);   
	  } catch (SQLException e) {
	   e.printStackTrace();
	  }   
	  return conn;
	}

	public List<T> queryPara(String sqlString, Object params[]) {
	  List<T> beans = null;
	  Connection conn = getConnection();
	  if (conn != null)
	  try {
	   QueryRunner qRunner = new QueryRunner();
	   beans = (List<T>) qRunner.query(conn, sqlString, params, new BeanListHandler(this.entityClass));
	  } catch (SQLException e) {   
	   // logger.error(e.getMessage());
	   e.printStackTrace();
	  } finally {
	   DbUtils.closeQuietly(conn);
	  }
	  return beans;
	}


	public List<T> queryNoPara(String sqlString) {
	  List<T> beans = null;
	  Connection conn = getConnection();
	  if (conn != null)
	  try {
	   QueryRunner qRunner = new QueryRunner();
	   beans = (List<T>) qRunner.query(conn, sqlString,new BeanListHandler(this.entityClass));
	  } catch (SQLException e) {   
	   // logger.error(e.getMessage());
	   e.printStackTrace();
	  } finally {
	   DbUtils.closeQuietly(conn);
	  }
	  return beans;
	}



	public T get(String sqlString, Object params[]) {
	  Object obj = null;
	  Connection conn = getConnection();
	  
	  if (conn != null)
	  try {
	   QueryRunner qRunner = new QueryRunner();
	   obj = qRunner.query(conn, sqlString, params, new BeanHandler(
	     this.entityClass));
	  } catch (SQLException e) {
	   // logger.error(e.getMessage());
	   e.printStackTrace();
	  } finally {
	   DbUtils.closeQuietly(conn);
	  }
	  return (T) obj;
	}


	public boolean update(String sqlString, Object params[]) {
	  Connection conn = getConnection();
	  boolean flag = false;
	  
	  if (conn != null)
	  try {
	   QueryRunner qRunner = new QueryRunner();
	   int i = qRunner.update(conn, sqlString, params);
	   if (i > 0) {
	    flag = true;
	   }
	  } catch (SQLException e) {
	   // logger.error(e.getMessage());
	   e.printStackTrace();
	  } finally {
	   DbUtils.closeQuietly(conn);
	  }
	  return flag;
	}


	protected Class getSuperClassGenricType(Class clazz, int index)
	   throws IndexOutOfBoundsException {
	  
	  Type genType = clazz.getGenericSuperclass();
	  // clazz.getGenericSuperclass();
	  if (!(genType instanceof ParameterizedType)) {
	   return Object.class;
	  }
	  Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
	  if (index >= params.length || index < 0) {
	   return Object.class;
	  }
	  if (!(params[index] instanceof Class)) {
	   return Object.class;
	  }
	  
	  return (Class) params[index];
	}


}
