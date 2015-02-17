package com.easy.persistance.sql.column;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.easy.kit.utils.string.StringSplitUtil;
import com.easy.persistance.sql.Field;
import com.easy.persistance.sql.IAssembleSql;
import com.easy.persistance.sql.select.ISelect;
import com.easy.persistance.sql.table.ITable;

public class Columns implements IAssembleSql {

	private LinkedList<IColumn> columnList = new LinkedList<IColumn>();
	private ITable table;
	public Columns(){}
	public Columns(ITable table) {
		this.table = table;
	}

	public void addColumn(ISelect select, String alias) {
		columnList.add(new SubSelectColumn(select.getSQL(), alias));
	}

	public void addColumn(String colName) {
		columnList.add(new Column(new Field(table,colName )));
	}

	public void addColumn(String... colName) { 
		for (String c : colName) {
			columnList.add(new Column(new Field(table,c)));
		}
	}
	public void addColumn(IColumn... cols) {
		for (IColumn c : cols) {
			columnList.add(c);
		}
	}

	public void addColumnWithAlias(String colName, String aliasName) {
		columnList.add(new Column(new Field(table,colName), aliasName));
	}

	public void addCustomColumn(String colName, String aliasName) {
		columnList.add(new CustomColumn(colName, aliasName));
	}

	public void addCustomColumn(CustomColumn column) {
		columnList.add(column);
	}

	public void addCountColumn() {
		columnList.add(new CountColumn(new Field(table,"*")));
	}
	public void addCountColumn(String colName) {
		columnList.add(new CountColumn(new Field(table,colName)));
	}
	public void addCountColumn(String colName,String aliasName) {
		columnList.add(new CountColumn(new Field(table,colName),aliasName));
	}
	public void addMaxColumn(String colName) {
		columnList.add(new MaxColumn(new Field(table,colName)));
	}
	public void addMaxColumn(String colName,String aliasName) {
		columnList.add(new MaxColumn(new Field(table,colName),aliasName));
	}
	public void addMinColumn(String colName) {
		columnList.add(new MinColumn(new Field(table,colName)));
	}
	public void addMinColumn(String colName,String aliasName) {
		columnList.add(new MinColumn(new Field(table,colName),aliasName));
	}
	public void addSumColumn(String colName) {
		columnList.add(new SumColumn(new Field(table,colName)));
	}
	public void addSumColumn(String colName,String aliasName) {
		columnList.add(new SumColumn(new Field(table,colName),aliasName));
	}
	public void addColumn(List<IColumn> columns) {
		for (IColumn c : columns) {
			columnList.add(c);
		}
	}

	public LinkedList<IColumn> getColumnList() {
		return columnList;
	}

	public void addColumn(Column col) {
		columnList.add(col);
	}

	public int getSize() {
		return columnList.size();
	}

	public void clear() {
		columnList.clear();
	}

	public String getAssembleSql(boolean isShowTableAlias) {
		if (columnList.size() == 0) {
			this.addColumn("*");
		}
		Iterator<IColumn> it = columnList.iterator();
		StringSplitUtil ss = new StringSplitUtil();
		while (it.hasNext()) {
			ss.append(it.next().getColumnSql(isShowTableAlias));
		}
		return ss.toString();

	}

	public String[] getArray() {
		return columnList.toArray(new String[0]);
	}
	public ITable getTable() {
		return table;
	}
	public void setTable(ITable table) {
		this.table = table;
	}
}
