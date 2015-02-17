package com.easy.persistance.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.easy.persistance.common.ConnectionFactory;
import com.easy.persistance.common.SQLAssistant;
import com.easy.persistance.exception.DAORuntimeException;

public class JdbcUtil {

	public static int[] statementBatch(String[] sqlArray) {
		Connection conn = ConnectionFactory.currentConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			for (String sql : sqlArray) {
				stmt.addBatch(sql);
			}
			return stmt.executeBatch();
		} catch (SQLException e) {
			throw new DAORuntimeException(e);
		} finally {
			close(stmt);
			ConnectionFactory.closeConnection();
		}
	}

	public static int statementUpdate(String sql) {
		Connection conn = ConnectionFactory.currentConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			return stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new DAORuntimeException(e);
		} finally {
			close(stmt);
			ConnectionFactory.closeConnection();
		}
	}

	public static int preparedStatementUpdate(String sql, List<Object> values) {
		Connection conn = ConnectionFactory.currentConnection();
		PreparedStatement prest = null;
		try {
			prest = conn.prepareStatement(sql);
			int index = 1;
			for (Object value : values) {
				prest.setObject(index++, value);
			}
			return prest.executeUpdate();
		} catch (SQLException e) {
			throw new DAORuntimeException(e);
		} finally {
			close(prest);
			ConnectionFactory.closeConnection();
		}
	}

	public static int[] preparedStatementBatch(String sql,
			List<List<Object>> valuesList) {
		Connection conn = ConnectionFactory.currentConnection();
		PreparedStatement prest = null;
		try {
			prest = conn.prepareStatement(sql);
			for (List<Object> values : valuesList) {
				int index = 1;
				for (Object value : values) {
					prest.setObject(index++, value);
				}
				prest.addBatch();
			}
			return prest.executeBatch();
		} catch (SQLException e) {
			throw new DAORuntimeException(e);
		} finally {
			close(prest);
			ConnectionFactory.closeConnection();
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DAORuntimeException("ResultSet关闭失败", e);
			}
			rs = null;
		}
	}

	public static void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DAORuntimeException("Statement关闭失败", e);
			}
			st = null;
		}
	}

	

	public static void setAutoCommit(Connection conn, boolean isAutoCommit) {
		try {
			conn.setAutoCommit(isAutoCommit);
		} catch (SQLException e) {
			throw new DAORuntimeException(e);
		}
	}

	public static void rollback(Connection conn) {
		try {
			conn.rollback();
		} catch (SQLException e) {
			throw new DAORuntimeException(e);
		}
	}

	public static void commit(Connection conn) {
		try {
			conn.commit();
		} catch (SQLException e) {
			throw new DAORuntimeException(e);
		}
	}
}
