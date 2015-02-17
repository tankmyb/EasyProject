package com.easy.persistance.resultset;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.easy.persistance.util.ColumnFormatUtil;
import com.easy.persistance.util.DataTypeUtil;

public abstract class AResultSet {
	protected List<String> columnList = new ArrayList<String>();
	protected List<IRow> rows;

	protected abstract void copy(ResultSet resultSet) throws SQLException;

	protected void formatFieldName(ResultSetMetaData rsmd) throws SQLException {
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			columnList.add(rsmd.getColumnLabel(i));
		}
	}

	public String[] getColumns() {
		return columnList.toArray(new String[0]);
	}

	public int columnSize() {
		return columnList.size();
	}

	public int size() {
		return rows.size();
	}

	public List<IRow> getRows() {
		return this.rows;
	}

	public IRow get(int index) {
		return this.rows.get(index);
	}

	public Iterator<IRow> iterator() {
		return rows.iterator();
	}

	public List<IRow> getSubList(int limit, int offset) {
		List<IRow> subRowList = rows.subList(limit, Math.min(limit + offset, this.size()));
		return subRowList;
	}

	public <T> List<T> getObjects(String columnName) throws Exception {
		List<T> result = new ArrayList<T>();
		for (int i = 0, n = rows.size(); i < n; i++) {
			T t = rows.get(i).get(columnName);
			result.add(t);
		}
		return result;
	}

	public List<Map<String, Object>> toJsonList() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(this.size());
		for (int i = 0, n = rows.size(); i < n; i++) {
			result.add(rows.get(i).getMap());
		}
		return result;
	}


	public String[][] toArray() throws Exception {
		return toArray(getColumns());
	}

	public String[][] toArray(String[] columns) throws Exception {
		String[][] array = new String[this.size()][columns.length];
		for (int i = 0, len = array.length; i < len; i++) {
			IRow cr = rows.get(i);
			Map<String, Object> map = cr.getMap();
			for (int j = 0, subLen = map.size(); j < subLen; j++) {
				array[i][j] = DataTypeUtil.judgeType(map.get(columnList.get(j)), true);
			}
		}
		return array;
	}

}
