package com.easy.persistance.sql.select.datebase;

import com.easy.persistance.sql.select.ASelect;
import com.easy.persistance.sql.table.ITable;

public class MysqlSelect extends ASelect {
	public MysqlSelect(ITable table) {
		super(table);
	}

	public MysqlSelect(ITable table, boolean isShowTableAlias) {
		super(table, isShowTableAlias);
	}

	@Override
	public String getSQL() {
		StringBuffer sql = new StringBuffer();
		sql.append(this.getSimpleSql());
		if (this.limit != null && this.limit!=0) {
			sql.append(" limit ").append(this.limit);
		}
		if (this.offset != null&& this.offset!=0) {
			sql.append(" offset ").append(this.offset);
		}
		return sql.toString();
	}
	
}
