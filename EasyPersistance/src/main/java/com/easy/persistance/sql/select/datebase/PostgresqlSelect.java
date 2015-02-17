package com.easy.persistance.sql.select.datebase;

import com.easy.persistance.sql.select.ASelect;
import com.easy.persistance.sql.table.ITable;

public  class PostgresqlSelect extends ASelect {
	public PostgresqlSelect(ITable table) {
		super(table);
	}
	public PostgresqlSelect(ITable table, boolean isShowTableAlias) {
		super(table, isShowTableAlias);
	}
	@Override
	public String getSQL() {
		StringBuffer sql = new StringBuffer();
		sql.append(this.getSimpleSql());
		if (this.limit != null) {
			sql.append(" limit ").append(this.limit);
		}
		if (this.offset != null) {
			sql.append(" offset ").append(this.offset);
		}
		return sql.toString();
	}
	
}
