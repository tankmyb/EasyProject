package com.easy.persistance.sql.column;

import com.easy.kit.utils.Tools;
import com.easy.persistance.sql.Field;

public class Column implements IColumn {
	protected Field field;
	protected String aliasName;

	public Column(Field field) {
		this.field = field;
	}

	public Column(Field field, String an) {
		this(field);
		aliasName = an;
	}

	@Override 
	public String getColumnSql(boolean isShowTableAlias) {
		StringBuffer col = new StringBuffer();
		col.append(field.getFieldSql(isShowTableAlias));
		if (Tools.isValid(aliasName)) {
			col.append(" as ").append(aliasName);
		}
		return col.toString();
	}
}
