package com.easy.persistance.sql.select.datebase;

import com.easy.persistance.sql.select.ASelect;
import com.easy.persistance.sql.table.ITable;
import com.easy.persistance.sql.table.Table;

public  class DB2Select extends ASelect {
	public DB2Select(Table table) {
		super(table);
	}
	public DB2Select(ITable table,boolean isShowTableAlias) {
		super(table,isShowTableAlias);
	}
	
	private String getTopSql(){
		StringBuffer sql = new StringBuffer();
		sql.append(this.getSimpleSql());
		sql.append(getSimpleSql());
		sql.append("  fetch first ");
		sql.append(offset);
		sql.append(" rows only ");
		return sql.toString();
	}
	private String getPaginationSql() {
		StringBuffer sql = new StringBuffer();
		String aliasName = this.getTable().getAliasName()+"tmp";
		sql.append("select * from (");
		sql.append("select ");
		sql.append(columns.getAssembleSql(false));
		sql.append(",rownumber() over(");
		if (orderBys.getSize() > 0) {
			sql.append("order by ").append(orderBys.getAssembleSql(false));
		}else {
			sql.append("order by ").append(this.primaryKeyName);
		}
		sql.append(") as rowid");
		sql.append(" from ");
		sql.append(table.getTableSql(false));
		if (wheres.getSize() > 0) {
			sql.append(" where (").append(wheres.getAssembleSql(false)).append(")");
		}
		if (groupBys.getSize() > 0) {
			sql.append(" group by ").append(groupBys.getAssembleSql(false));
		}
		sql.append(") as ");
		sql.append(aliasName);
		sql.append(" where ");
		sql.append(aliasName);
		sql.append(".rowid>");
		sql.append(offset);
		sql.append(" and ");
		sql.append(aliasName);
		sql.append(".rowid<=");
		sql.append(offset+limit);
	    return sql.toString();
	}
	@Override
	public String getSQL() {
		if(limit != null && offset != null){
			return this.getPaginationSql();
		}
		if(offset != null){
          return this.getTopSql();
		}
		return this.getSimpleSql();
	}
	
	
}
