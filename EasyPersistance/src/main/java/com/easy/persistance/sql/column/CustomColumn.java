package com.easy.persistance.sql.column;


public class CustomColumn implements IColumn{
    private String colName;
    private String colAlias;
	public CustomColumn(String colName,String colAlias) {
		this.colName=colName;
		this.colAlias=colAlias;
	}
	public String getColumnSql(boolean isShowTableAlias) {
		StringBuffer col = new StringBuffer(colName);
		col.append(" as ").append(colAlias);
		return col.toString();
	}
}
