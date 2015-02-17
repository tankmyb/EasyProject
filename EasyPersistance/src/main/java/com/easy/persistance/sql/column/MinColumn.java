package com.easy.persistance.sql.column;

import com.easy.kit.utils.Tools;
import com.easy.persistance.sql.Field;

public class MinColumn extends Column {

	public MinColumn(Field field) {
		super(field);
	}

	public MinColumn(Field field, String an) {
		super(field, an);
	}
	@Override
	public String getColumnSql(boolean isShowTableAlias) {
		StringBuffer col = new StringBuffer("mix(");
		col.append(field.getFieldSql(isShowTableAlias));
		col.append(")");

		if (Tools.isValid(aliasName)) {
			col.append(" as ").append(aliasName);
		}
		return col.toString();
	}
	
}