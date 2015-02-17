package com.easy.persistance.sql.select.datebase;

import com.easy.persistance.sql.select.ASelect;
import com.easy.persistance.sql.table.ITable;

public  class Mssql2005Select extends ASelect {
	public Mssql2005Select(ITable table) {
		super(table);
	}
	public Mssql2005Select(ITable table, boolean isShowTableAlias) {
		super(table, isShowTableAlias);
	}

	private String getOrderBySql() {
		if (orderBys.getSize() > 0) {
			return orderBys.getAssembleSql(false);
		}else {
			return primaryKeyName;
		}
	}
	private String getTopSql() {
		StringBuffer sql = new StringBuffer("select ");
		sql.append("top ").append(limit).append(" ");
		sql.append(columns.getAssembleSql(isShowTableAlias)).append(" from ");
		sql.append(table.getTableSql(isShowTableAlias));
		if (wheres.getSize() > 0) {
			sql.append(" where (").append(wheres.getAssembleSql(isShowTableAlias)).append(")");
		}
		if (groupBys.getSize() > 0) {
			sql.append(" group by ").append(groupBys.getAssembleSql(isShowTableAlias));
		}
		if (orderBys.getSize() > 0) {
			sql.append(" order by ").append(orderBys.getAssembleSql(isShowTableAlias));
		}
		return sql.toString();
	
	}
	private String getPaginationSql() {
		int intStart = offset;
		int intLimit = offset == 0 ? limit : offset + limit;
		StringBuffer sb = new StringBuffer("SELECT * FROM(");
		sb.append("SELECT ROW_NUMBER() OVER (ORDER BY ").append(getOrderBySql());
		sb.append(") AS row_num,").append(columns.getAssembleSql(false));
		sb.append(" from ").append(table.getTableSql(false));
		if (wheres.getSize() > 0) {
			sb.append(" where (").append(wheres.getAssembleSql(false)).append(")");
		}
		if (groupBys.getSize() > 0) {
			sb.append(" group by ").append(groupBys.getAssembleSql(false));
		}
		sb.append(") row_num_table where row_num between ").append(intStart).append(" and ").append(intLimit);
		return sb.toString();
	}

	@Override
	public String getSQL() {
		if (offset != null && limit != null) {
			return getPaginationSql();
		}
		if (offset != null) {
			return getTopSql();
		}
		return getSimpleSql();
	}
	
}
