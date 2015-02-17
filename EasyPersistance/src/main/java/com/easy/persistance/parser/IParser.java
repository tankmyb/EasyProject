package com.easy.persistance.parser;

/**
 * 
 * 组装SQL与获取参数
 *
 */
public interface IParser<T> {

	public T getValues();
	public String getSQL();
}
