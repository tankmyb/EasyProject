package com.easy.persistance.resultset;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.easy.persistance.util.ColumnFormatUtil;

public class CustomResultSet extends AResultSet implements IResultSet {
	private boolean isFormatFieldName = true;

	public CustomResultSet() {
		rows = new LinkedList<IRow>();
	}

	public CustomResultSet(List<String> columnList) {
		this();
		this.columnList = columnList;
	}

	public CustomResultSet(String... columns) {
		this();
		for (String c : columns) {
			this.columnList.add(c);
		}
	}

	public CustomResultSet(ResultSet rs, boolean isFormatFieldName) throws SQLException {
		this();
		this.isFormatFieldName = isFormatFieldName;
		copy(rs);
	}

	protected void copy(ResultSet resultSet) throws SQLException {
		String columnName = null;
		ResultSetMetaData rsmd = resultSet.getMetaData();
		while (resultSet.next()) {
			IRow row = new CustomRow();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String name = rsmd.getColumnLabel(i);
				Object value = resultSet.getObject(name);
				columnName = name;
				row.set(columnName, value);
			}
			rows.add(row);
		}
		formatFieldName(rsmd);
	}

	public void addItem(IRow row) {
		rows.add(row);
	}

	public void insert(int index, IRow row) {
		this.rows.add(index, row);
	}



}
