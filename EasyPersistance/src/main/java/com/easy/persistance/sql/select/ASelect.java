package com.easy.persistance.sql.select;

import java.util.List;

import com.easy.persistance.sql.Field;
import com.easy.persistance.sql.column.Columns;
import com.easy.persistance.sql.column.CustomColumn;
import com.easy.persistance.sql.column.IColumn;
import com.easy.persistance.sql.condition.Conditions;
import com.easy.persistance.sql.condition.ICondition;
import com.easy.persistance.sql.groupby.GroupBy;
import com.easy.persistance.sql.groupby.GroupBys;
import com.easy.persistance.sql.orderby.OrderBy;
import com.easy.persistance.sql.orderby.OrderBys;
import com.easy.persistance.sql.table.ITable;

public abstract class ASelect implements ISelect{

	protected Columns columns;
	protected ITable table;
	protected Conditions wheres;
	protected OrderBys orderBys;
	protected GroupBys groupBys;
	protected String primaryKeyName;
	protected Integer limit;
	protected Integer offset;
	protected boolean isShowTableAlias = true;
	
	public ASelect(ITable table) {
		this.table = table;
		columns = new Columns(table);
		wheres = new Conditions(table);
		orderBys = new OrderBys(table);
		groupBys = new GroupBys(table);
	}
	public ASelect(ITable table,boolean isShowTableAlias) {
		this(table);
		this.isShowTableAlias = isShowTableAlias;
	}
	public void clear() {
		if (columns != null && columns.getSize() > 0) {
			columns.clear();
			columns = null;
		}
		if (wheres != null && wheres.getSize() > 0) {
			wheres.clear();
			wheres = null;
		}
		if (orderBys != null && orderBys.getSize() > 0) {
			orderBys.clear();
			orderBys = null;
		}
		if (groupBys != null && groupBys.getSize() > 0) {
			groupBys.clear();
			groupBys = null;
		}
		
		table = null;
		
		
	}

	// ------------------addColumns-----------------
	public void addCountColumn() {
		columns.addCountColumn();
	}

	public void addCountColumn(String col) {
		columns.addCountColumn(col);
	}

	public void addCountColumn(String col, String alias) {
		columns.addCountColumn(col, alias);
	}

	public void addMinColumn(String col) {
		columns.addMinColumn(col);
	}

	public void addMinColumn(String col, String alias) {
		columns.addMinColumn(col, alias);
	}

	public void addMaxColumn(String col) {
		columns.addMaxColumn(col);
	}

	public void addMaxColumn(String col, String alias) {
		columns.addMaxColumn(col, alias);
	}

	public void addSumColumn(String col) {
		columns.addSumColumn(col);
	}

	public void addSumColumn(String col, String alias) {
		columns.addSumColumn(col, alias);
	}

	public void addColumns(IColumn... cols) {
		columns.addColumn(cols);
	}
	public void addColumns(List<IColumn> list) {
		columns.addColumn(list);
	}

	public void addColumns(ISelect select, String alias) {
		columns.addColumn(select, alias);
	}

	public void addColumnWithAlias(String colName, String aliasName) {
		columns.addColumnWithAlias(colName, aliasName);
	}

	public void addColumns(String... colName) {
		columns.addColumn(colName);
	}
	public void addAllColumn(){
		columns.addColumn("*");
	}

	public void addCustomColumn(CustomColumn column) {
		columns.addCustomColumn(column);
	}

	public void addCustomColumn(String colName, String aliasName) {
		columns.addCustomColumn(colName, aliasName);
	}

	// ------------------addColumns-----------------

	// ------------------addOrderBys-----------------
	public void addOrderBys(String... colName) {
		orderBys.addOrderBy(colName);
	}

	public void addOrderBys(boolean isDesc, String... colName) {
		orderBys.addOrderBy(isDesc, colName);
	}

	public void addOrderBys(List<OrderBy> orderByList) {
		orderBys.addOrderBy(orderByList);
	}
	public void addFirstOrderBy(String colName){
		orderBys.addFirstOrderBy(colName);
	}
	// ------------------addOrderBys-----------------
	// ------------------addGroupBys-----------------
	public void addGroupBys(String... colName) {
		groupBys.addGroupBy(colName);
	}

	public void addGroupBys(List<GroupBy> groupByList) {
		groupBys.addGroupBy(groupByList);
	}

	// ------------------addGroupBys-----------------
	// ------------------addWheres-------------------
	// equal
	public <T> void addEqualCondition(String colName, T value) {
		wheres.addEqualCondition(colName, value);
	}

	// notEqual
	public <T> void addNotEqualCondition(String colName, T value) {
		wheres.addNotEqualCondition(colName, value);
	}

	// Less
	public <T> void addLessCondition(String colName, T value) {
		wheres.addLessCondition(colName, value);
	}

	// LessEqual
	public <T> void addLessEqualCondition(String colName, T value) {
		wheres.addLessEqualCondition(colName, value);
	}

	// More
	public <T> void addMoreCondition(String colName, T value) {
		wheres.addMoreCondition(colName, value);
	}

	// MoreEqual
	public <T> void addMoreEqualCondition(String colName, T value) {
		wheres.addMoreEqualCondition(colName, value);
	}

	

	/**
	 * 不需要再加%
	 * @param colName
	 * @param value
	 */
	public void addLikeCondition(String colName, String value) {
		wheres.addLikeCondition(colName, value);
	}

	// Between and
	public <T> void addBetweenCondition(String colName, T startValue, T endValue) {
		wheres.addBetweenCondition(colName, startValue, endValue);
	}

	/**
	 * in数组
	 * @param <T>
	 * @param colName
	 * @param value
	 */
	public <T> void addInCondition(String colName, T[] value)  {
		wheres.addInArrayCondition(colName, value);
	}
	/**
	 * in List
	 * @param <T>
	 * @param colName
	 * @param value
	 */
	public <T> void addInCondition(String colName, List<T> value)  {
		wheres.addInListCondition(colName, value);
	}
	/**
	 * is null
	 * @param colName
	 */
	public void addIsNullCondition(String colName) {
		wheres.addIsNullCondition(colName);
	}
    /**
     * is not null
     * @param colName
     */
	public void addIsNotNullCondition(String colName) {
		wheres.addIsNotNullCondition(colName);
	}
	public void addInSubSelectCondition(String colName, ISelect select)  {
		wheres.addInSubSelectCondition(colName, select);
	}
	public void addConditions(ICondition logicTree) {
		wheres.addCondition(logicTree);
	}

	public void addConditions(List<ICondition> whereList) {
		wheres.addCondition(whereList);
	}
	
	// ------------------addWheres-------------------


	public Field createField(String colName) {
		return new Field(this.table, colName);
	}

	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}


	public Columns getColumns() {
		return columns;
	}

	public ITable getTable() {
		return table;
	}

	public Conditions getWheres() {
		return wheres;
	}

	public OrderBys getOrderBys() {
		return orderBys;
	}

	public GroupBys getGroupBys() {
		return groupBys;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public void finalize() {
		this.clear();
	}
	
	public Integer getLimit() {
		return limit;
	}

	public Integer getOffset() {
		return offset;
	}
	public String getPrimaryKeyName() {
		return primaryKeyName;
	}
	protected String getSimpleSql(){
		StringBuffer sb = new StringBuffer("select ");
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
		return sb.toString();
	}
	@Override
    public String getCountSql(){
    	StringBuffer sb = new StringBuffer("select count(*)");
		sb.append(" from ").append(table.getTableSql(isShowTableAlias));
		if (wheres.getSize() > 0) {
			sb.append(" where (").append(wheres.getAssembleSql(isShowTableAlias)).append(")");
		}
		return sb.toString();
    }
    public List<Object> getValues(){
    	return wheres.getValues();
    }
}
