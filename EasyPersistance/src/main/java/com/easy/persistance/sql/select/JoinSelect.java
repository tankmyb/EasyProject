package com.easy.persistance.sql.select;

import java.util.List;

import com.easy.persistance.sql.Field;
import com.easy.persistance.sql.column.Columns;
import com.easy.persistance.sql.column.IColumn;
import com.easy.persistance.sql.condition.Conditions;
import com.easy.persistance.sql.condition.ICondition;
import com.easy.persistance.sql.condition.on.Ons;
import com.easy.persistance.sql.groupby.GroupBy;
import com.easy.persistance.sql.groupby.GroupBys;
import com.easy.persistance.sql.orderby.OrderBy;
import com.easy.persistance.sql.orderby.OrderBys;

public class JoinSelect implements ISelect {
	
	private ASelect[] selects;
	private Columns columns;
	private Conditions wheres;
	private OrderBys orderBys;
	private GroupBys groupBys;
	private Ons ons;
	public JoinSelect(ASelect... selects) {
		ons = new Ons();
		columns = new Columns();
		wheres = new Conditions();
		orderBys = new OrderBys();
		groupBys = new GroupBys();
		this.selects = selects;
		getAssembleSql();
	}


	private void addColumns(List<IColumn> columns) {
		for (IColumn c : columns) {
			this.columns.addColumn(c);
		}
	}

	private void addWheres(List<ICondition> list) {
		this.wheres.addCondition(list);

	}

	private void addOrderBys(List<OrderBy> list) {
		this.orderBys.addOrderBy(list);
	}

	private void addGroupBys(List<GroupBy> list) {
		this.groupBys.addGroupBy(list);
	}

	private void getAssembleSql() {
		for (ASelect s : selects) {
			this.addColumns(s.getColumns().getColumnList());
			this.addWheres(s.getWheres().getConditionList());
			this.addOrderBys(s.getOrderBys().getOrderByList());
			this.addGroupBys(s.getGroupBys().getGroupByList());
		}

	}
	public void clear() {
		for (ASelect s : selects) {
			s.clear();
		}
		ons.clear();
		ons = null;
	}

	public void addOnCondition(Field leftField, Field rightField, String joinType) {
		ons.addOnCondition(leftField, rightField, joinType);
	}
	public void addRightOnCondition(Field leftField, Field rightField, String joinType) {
		ons.addRightOnCondition(leftField, rightField, joinType);
	}
	public void addOnCondition(Field leftField, Field rightField, ICondition logic, String joinType) {
		ons.addOnCondition(leftField, rightField, logic, joinType);
	}

	
	public String getSQL() {
		StringBuffer sb = new StringBuffer("select ");
		sb.append(columns.getAssembleSql(true)).append(" from ");
		sb.append(ons.getAssemble());
		if (wheres.getSize() > 0) {
			sb.append(" where (").append(wheres.getAssembleSql(true)).append(")");
		}
		if (groupBys.getSize() > 0) {
			sb.append(" group by ").append(groupBys.getAssembleSql(true));
		}
		if (orderBys.getSize() > 0) {
			sb.append(" order by ").append(orderBys.getAssembleSql(true));
		}
		
		return sb.toString();
	}

	public void finalize() {
		this.clear();
	}


	@Override
	public List<Object> getValues() {
		return wheres.getValues();
	}


	@Override
	public String getCountSql() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
