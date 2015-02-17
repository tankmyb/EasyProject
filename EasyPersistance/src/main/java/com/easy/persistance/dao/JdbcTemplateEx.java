package com.easy.persistance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.easy.persistance.aop.PrintSqlAnnotation;
import com.easy.persistance.aop.PrintSqlListAnnotation;
import com.easy.persistance.resultset.DBResultSet;

public class JdbcTemplateEx implements IJdbcTemplateEx {
	@Resource
	private JdbcTemplate jdbcTemplate;


	@Override
	@PrintSqlAnnotation
	public DBResultSet queryForDBResultSet(final String sql,List<Object> values) {
		Object[] valueArray = (Object[])values.toArray(new Object[values.size()]);
		DBResultSet dbrs = jdbcTemplate.query(sql,valueArray,
				new ResultSetExtractor<DBResultSet>() {
					@Override
					public DBResultSet extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						return new DBResultSet(rs);
					}
				});
		return dbrs;
	}

	private int execute(final String sql, final List<Object> values) {
		return jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql);
				int index = 1;
				for (Object v : values) {
					ps.setObject(index++, v);
				}
				return ps;
			}
		});
	}

	@Override
	@PrintSqlAnnotation
	public void insert(final String sql, final List<Object> values) {
		execute(sql, values);
	}

	@Override
	@PrintSqlAnnotation
	public int insertWithGeneratedKey(final String sql,
			final List<Object> values) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);// 自增
				int index = 1;
				for (Object v : values) {
					ps.setObject(index++, v);
				}
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	@PrintSqlAnnotation
	public int update(final String sql, final List<Object> values) {
		return execute(sql, values);

	}

	@Override
	@PrintSqlAnnotation
	public int delete(String sql,List<Object> values) {
		return execute(sql, values);
	}

	@Override
	@PrintSqlAnnotation
	public int count(String sql,List<Object> values) {
		Object[] valueArray = (Object[])values.toArray(new Object[values.size()]);
		return jdbcTemplate.queryForInt(sql,valueArray);
	}

	@Override
	@PrintSqlListAnnotation
	public int[] batchInsert(String sql, final List<List<Object>> valuesList) {
		return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				List<Object> values = valuesList.get(i);
				int index = 1;
				for (Object v : values) {
					ps.setObject(index++, v);
				}
			}

			@Override
			public int getBatchSize() {
				return valuesList.size();
			}
		});

	}

}
