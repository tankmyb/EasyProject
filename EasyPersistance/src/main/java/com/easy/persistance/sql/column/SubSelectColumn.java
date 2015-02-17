package com.easy.persistance.sql.column;


public class SubSelectColumn extends AColumn {
	public SubSelectColumn(String subSelect, String alaisName) {
		this.colName = subSelect;
		this.aliasName = alaisName;
	}

	public String getColumnSql(boolean isShowTableAlias) {
		StringBuffer col = new StringBuffer();
		col.append("(").append(colName).append(")");
		col.append(" as ").append(aliasName);
		return col.toString();
	}
}
