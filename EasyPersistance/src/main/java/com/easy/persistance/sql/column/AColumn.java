package com.easy.persistance.sql.column;

import com.easy.persistance.sql.table.Table;

public abstract class AColumn implements IColumn {

	protected String colName;
	protected String aliasName;
	protected Table table;

	public AColumn() {

	}

	public AColumn(String col, Table table) {
		colName = col;
		this.table = table;
	}

	public AColumn(String col, String an, Table table) {
		colName = col;
		aliasName = an;
		this.table = table;
	}
	@Override
	public String getColumnSql(boolean isShowTableAlias) {
		return null;
	}


}
