package com.easy.persistance.common;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import com.easy.persistance.exception.DAORuntimeException;
import com.easy.persistance.sql.select.datebase.DB2Select;
import com.easy.persistance.sql.select.datebase.Mssql2000Select;
import com.easy.persistance.sql.select.datebase.Mssql2005Select;
import com.easy.persistance.sql.select.datebase.MysqlSelect;
import com.easy.persistance.sql.select.datebase.OracleSelect;
import com.easy.persistance.sql.select.datebase.PostgresqlSelect;

public class SQLAssistant {
	public final static String INNERJOIN = "inner join",
			LEFTJOIN = "left join", RIGHTJOIN = "right join";
	public final static String PostgreSQLDatabaseName = "PostgreSQL";
	public final static String MysqlDatabaseName = "Mysql";
	public final static String MssqlDatabaseName = "Microsoft SQL Server";
	public final static String DB2DatabaseName = "DB2";
	public final static String OracleDatabaseName = "Oracle";
	public final static String C3p0PoolName = "c3p0";
	public final static String InsertType = "insert";
	public final static String UpdatetType = "update";
	public final static String DeleteType = "delete";
	private static String DatabaseName = null;
	private static String DatabaseViesion = null;
	public static boolean isShowSql = new Boolean(DatasourceResourceBundle.getString("showSql"));
	/**
	 * 判断是否连接postgreSQL数据库
	 * 
	 * @return
	 */
	public static boolean isPostgreSQLDatabase() {
		return PostgreSQLDatabaseName.equalsIgnoreCase(DatabaseName);
	}

	/**
	 * 判断是否连接mysql数据库
	 * 
	 * @return
	 */
	public static boolean isMysqlDatabase() {
		return MysqlDatabaseName.equalsIgnoreCase(DatabaseName);
	}

	/**
	 * 判断是否连接mssql数据库
	 * 
	 * @return
	 */
	public static boolean isMssqlDatabase() {
		return MssqlDatabaseName.equalsIgnoreCase(DatabaseName);
	}

	/**
	 * 判断是否连接mssql 2000数据库
	 * 
	 * @return
	 */
	public static boolean isMssql2000Database() {
		return isMssqlDatabase() && DatabaseViesion.indexOf("2000") != -1;
	}

	/**
	 * 判断是否连接mssql 2005数据库
	 * 
	 * @return
	 */
	public static boolean isMssql2005Database() {
		return isMssqlDatabase() && (DatabaseViesion.indexOf("2005") != -1);
	}

	/**
	 * 判断是否连接mssql 2008数据库
	 * 
	 * @return
	 */
	public static boolean isMssql2008Database() {
		return isMssqlDatabase() && (DatabaseViesion.indexOf("10") != -1);
	}

	/**
	 * 判断是否连接DB2数据库
	 * 
	 * @return
	 */
	public static boolean isDB2Database() {
		return DatabaseName.startsWith(DB2DatabaseName);
	}

	/**
	 * 判断是否连接postgreSQL数据库
	 * 
	 * @return
	 */
	public static boolean isOracleDatabase() {
		return OracleDatabaseName.equalsIgnoreCase(DatabaseName);
	}

	public static void setDatabaseName(String databaseName) {
		DatabaseName = databaseName;
	}

	public static void setDatabaseViesion(String databaseViesion) {
		DatabaseViesion = databaseViesion;
	}

	public static String getDatabaseName() {
		return DatabaseName;
	}

	public static String getDatabaseViesion() {
		return DatabaseViesion;
	}

	/**
	 * 根据数据库名查找相应的select类
	 * 
	 * @return
	 */
	public static String getSelectByDatabaseName() {
		if (isMysqlDatabase()) {
			return MysqlSelect.class.getName();
		} else if (isMssql2000Database()) {
			return Mssql2000Select.class.getName();
		} else if (isMssql2005Database() || isMssql2008Database()) {
			return Mssql2005Select.class.getName();
		} else if (isDB2Database()) {
			return DB2Select.class.getName();
		} else if (isPostgreSQLDatabase()) {
			return PostgresqlSelect.class.getName();
		} else if (isPostgreSQLDatabase()) {
			return PostgresqlSelect.class.getName();
		} else if (isOracleDatabase()) {
			return OracleSelect.class.getName();
		} else {
			throw new DAORuntimeException("连接未定义的数据库");
		}
	}
	public static void getDatabaseNameAndVersion(Connection conn) {
		DatabaseMetaData meta = null;
		try {
			meta = conn.getMetaData();
			SQLAssistant.setDatabaseName(meta.getDatabaseProductName());
			SQLAssistant.setDatabaseViesion(meta.getDatabaseProductVersion());
		} catch (SQLException e) {
			throw new DAORuntimeException("获取数据库名和版本号失败", e);
		}
	}
	
}
