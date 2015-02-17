package com.easy.persistance.resultset;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.easy.persistance.util.ColumnFormatUtil;

public class DBResultSet extends AResultSet implements IResultSet {

	public DBResultSet() {
		rows = new ArrayList<IRow>();
	}

	public DBResultSet(ResultSet rs) throws SQLException {
		this();
		copy(rs);
	}
    @Override
	protected void copy(ResultSet resultSet) throws SQLException {
		//String columnName = null;
		ResultSetMetaData rsmd = resultSet.getMetaData();
		while (resultSet.next()) {
			IRow row = new DBRow(); 
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String name = rsmd.getColumnLabel(i);
				Object value = resultSet.getObject(name);
				//columnName = ColumnFormatUtil.upperCaseName(name,false);
				row.set(name, value);
			}
			rows.add(row);
		}
		formatFieldName(rsmd);
	}

	

}
