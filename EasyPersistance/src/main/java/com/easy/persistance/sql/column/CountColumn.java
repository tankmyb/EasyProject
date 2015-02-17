package com.easy.persistance.sql.column;

import com.easy.kit.utils.Tools;
import com.easy.persistance.sql.Field;

public class CountColumn extends Column {

	public CountColumn(Field field) {
		super(field);
	}

	public CountColumn(Field field, String an) {
		super(field, an);
	}
	@Override
	public String getColumnSql(boolean isShowTableAlias) {
		StringBuffer col = new StringBuffer("count(");
		col.append(field.getFieldSql(isShowTableAlias));
		col.append(")");

		if (Tools.isValid(aliasName)) {
			col.append(" as ").append(aliasName);
		}
		return col.toString();
	}
}
