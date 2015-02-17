package com.easy.persistance.sql.table;

public interface ITable {

	public String getTableSql(boolean isShowTableAlias);
	
	public String getAliasName();
	
	public String getTableName();
}
