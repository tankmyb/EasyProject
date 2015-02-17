package com.easy.persistance.sql.select.datebase;

import com.easy.persistance.sql.Field;
import com.easy.persistance.sql.select.ASelect;
import com.easy.persistance.sql.table.ITable;

public class Mssql2000Select extends ASelect {
	public Mssql2000Select(ITable table) {
		super(table);
	}

	public Mssql2000Select(ITable table, boolean isShowTableAlias) {
		super(table, isShowTableAlias);
	}

	private String getTopSql() {
		StringBuffer sql = new StringBuffer();
		sql.append("select top ").append(limit).append(" ");
		sql.append(columns.getAssembleSql(isShowTableAlias)).append(" from ");
		sql.append(table.getTableSql(isShowTableAlias));
		if (wheres.getSize() > 0) {
			sql.append(" where (").append(
					wheres.getAssembleSql(isShowTableAlias)).append(")");
		}
		if (groupBys.getSize() > 0) {
			sql.append(" group by ").append(
					groupBys.getAssembleSql(isShowTableAlias));
		}
		if (orderBys.getSize() > 0) {
			sql.append(" order by ").append(
					orderBys.getAssembleSql(isShowTableAlias));
		}
		return sql.toString();
	}

	private String getPaginationSql() {
		if (this.primaryKeyName == null) {
			throw new RuntimeException("主键名不能为空");
		}
		Field pkField = new Field(table, primaryKeyName);
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(columns.getAssembleSql(isShowTableAlias)).append(" from ");
		sql.append(table.getTableSql(isShowTableAlias));
		if (wheres.getSize() > 0) {
			sql.append(" where (").append(wheres.getAssembleSql(true)).append(
					")");
			sql.append(" and ");
		} else {
			sql.append(" where ");
		}
		sql.append(pkField.getFieldSql()).append(">");
		sql.append("(select max(").append(primaryKeyName).append(") from");
		sql.append(" (select top ").append(offset).append(" ").append(
				primaryKeyName);
		sql.append(" from ").append(table.getTableName()).append(" order by ")
				.append(primaryKeyName);
		sql.append(") as T)");
		if (groupBys.getSize() > 0) {
			sql.append(" group by ").append(
					groupBys.getAssembleSql(isShowTableAlias));
		}
		this.orderBys.addFirstOrderBy(this.primaryKeyName);
		sql.append(orderBys.getAssembleSql(isShowTableAlias));
		return sql.toString();
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

	public String getSubSQL() {
		return null;
	}

}
