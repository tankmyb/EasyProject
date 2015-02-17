package com.easy.persistance.dao;

import java.util.List;

import com.easy.persistance.resultset.DBResultSet;

/**
 * 所有方法都要按以下约定
 * 第一个参数一定要是sql语句，如果sql需要传入参数的话，则放在第二参数里
 *
 */
public interface IJdbcTemplateEx {
	
	 DBResultSet queryForDBResultSet(String sql,List<Object> values);
	 void insert(String sql,List<Object> values);
	 int[] batchInsert(String sql,List<List<Object>> values);
	 int update(String sql,List<Object> values);
	 int delete(String sql,List<Object> values);
	 int insertWithGeneratedKey(String sql,List<Object> values);
	 int count(String sql,List<Object> values);
}
