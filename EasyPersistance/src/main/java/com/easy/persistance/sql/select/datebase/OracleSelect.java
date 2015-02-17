package com.easy.persistance.sql.select.datebase;

import com.easy.persistance.sql.select.ASelect;
import com.easy.persistance.sql.table.ITable;
import com.easy.persistance.sql.table.TableSelect;

public class OracleSelect extends ASelect {
	public OracleSelect(ITable table) {
		super(table);
	}

	public OracleSelect(ITable table, boolean isShowTableAlias) {
		super(table, isShowTableAlias);
	}

	private String getTopSql(){
		StringBuffer sql = new StringBuffer();
		if(orderBys.getSize()>0){
			TableSelect ts = new TableSelect(table,table.getAliasName(), false);
			ts.addAllColumn();
			ts.addOrderBys(this.orderBys.getAssembleSql(false));
			sql.append("select ");
			sql.append(this.columns.getAssembleSql(true));
			sql.append(" from ").append(ts.getTableSql(true));
			//wheres.addCustomCondition("ROWNUM", offset, ConditionEnum.LESSEQAL.getValue());
			sql.append(" where (").append(wheres.getAssembleSql(true)).append(")");
			if (groupBys.getSize() > 0) {
				sql.append(" group by ").append(groupBys.getAssembleSql(true));
			}
		}else {
			sql.append("select ");
			sql.append(this.columns.getAssembleSql(true));
			sql.append(" from ").append(table.getTableSql(true));
			//wheres.addCustomCondition("ROWNUM", offset, ConditionEnum.LESSEQAL.getValue());
			sql.append(" where (").append(wheres.getAssembleSql(true)).append(")");
			if (groupBys.getSize() > 0) {
				sql.append(" group by ").append(groupBys.getAssembleSql(true));
			}
		}
		return sql.toString();
	}
	private String getPaginationSql() {
		StringBuffer sql = new StringBuffer();
		TableSelect ts1 = new TableSelect(table, "t1tmp", false);
		ts1.addAllColumn();
		orderBys.addFirstOrderBy(getPrimaryKeyName());
		ts1.addOrderBys(this.orderBys.getAssembleSql(false));

		TableSelect ts2 = new TableSelect(ts1, table.getAliasName(), true);
		ts2.addAllColumn();
		ts2.addCustomColumn("ROWNUM", "ro");
		//ts2.addCustomCondition("ROWNUM", limit + offset, ConditionEnum.LESSEQAL
				//.getValue());

		sql.append("select ");
		sql.append(this.columns.getAssembleSql(true));
		sql.append(" from ").append(ts2.getTableSql(true));
		wheres.addMoreCondition("ro", offset);
		sql.append(" where (").append(wheres.getAssembleSql(true)).append(")");
		if (groupBys.getSize() > 0) {
			sql.append(" group by ").append(groupBys.getAssembleSql(true));
		}

		return sql.toString();

	}

	@Override
	public String getSQL() {
		if (offset != null && limit != null) {
			return getPaginationSql();
		}
		if(offset != null){
			return getTopSql();
		}
		return getSimpleSql();
	}

	
}
