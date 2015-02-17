package com.easy.persistance.sql.column;

import com.easy.kit.utils.Tools;
import com.easy.persistance.sql.Field;
import com.easy.persistance.sql.table.Table;

public class SumColumn extends Column {

	public SumColumn(Field field) {
		super(field);
	}

	public SumColumn(Field field, String an) {
		super(field, an);
	}
    @Override
	public String getColumnSql(boolean isShowTableAlias) {
		StringBuffer col = new StringBuffer("sum(");
		col.append(field.getFieldSql(isShowTableAlias));
		col.append(")");

		if (Tools.isValid(aliasName)) {
			col.append(" as ").append(aliasName);
		}
		return col.toString();
	}
	
	
}
