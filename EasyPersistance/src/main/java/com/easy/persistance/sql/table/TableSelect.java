package com.easy.persistance.sql.table;

import com.easy.persistance.sql.select.ASelect;

public class TableSelect extends ASelect implements ITable{
	private String aliasName;
	public TableSelect(ITable table) {
		super(table);
	}
	public TableSelect(ITable table,boolean isShowTableAlias) {
		super(table,isShowTableAlias);
	}
	public TableSelect(ITable table,String selectAlias) {
		this(table);
		this.aliasName = selectAlias;
	}
	public TableSelect(ITable table,String selectAlias,boolean isShowTableAlias) {
		this(table,isShowTableAlias);
		this.aliasName = selectAlias;
	}
		
	@Override
	public String getAliasName() {
		return aliasName;
	}
	@Override
	public String getTableName() {
		StringBuffer sb = new StringBuffer("(select ");
		sb.append(columns.getAssembleSql(isShowTableAlias)).append(" from ");
			sb.append(table.getTableSql(isShowTableAlias));
		
		if (wheres.getSize() > 0) {
			sb.append(" where (").append(wheres.getAssembleSql(isShowTableAlias)).append(")");
		}
		if (groupBys.getSize() > 0) {
			sb.append(" group by ").append(groupBys.getAssembleSql(isShowTableAlias));
		}
		if (orderBys.getSize() > 0) {
			sb.append(" order by ").append(orderBys.getAssembleSql(isShowTableAlias));
		}
		sb.append(") ");
		return sb.toString();
	}
	@Override
	public String getTableSql(boolean isShowTableAlias) {
		StringBuffer sb = new StringBuffer(getTableName());
		if(aliasName != null){
			sb.append(aliasName);
		}
		return sb.toString();
	}
	@Override
	public String getSQL() {
		return null;
	}

}
