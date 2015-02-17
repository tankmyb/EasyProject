package com.easy.persistance.sql.orderby;

import com.easy.kit.utils.Tools;
import com.easy.persistance.sql.Field;

public class OrderBy implements IOrderBy {

	private Field field;
	private boolean isDesc;

	public OrderBy(Field field) {
		this.field = field;
	}

	public OrderBy(Field field, boolean isDesc) {
		this(field);
		this.isDesc = isDesc;
	}

	@Override
	public String getOrderBySql(boolean isShowTableAlias) {
		StringBuffer sb = new StringBuffer();
		sb.append(field.getFieldSql(isShowTableAlias));
		if (Tools.isValid(this.isDesc)) {
			if (this.isDesc) {
				sb.append(" desc");
			}
		}
		return sb.toString();
	}

	public Boolean getIsDesc() {
		return isDesc;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
}
