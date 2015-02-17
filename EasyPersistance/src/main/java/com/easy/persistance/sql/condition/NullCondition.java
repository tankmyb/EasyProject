package com.easy.persistance.sql.condition;

import java.util.List;

import com.easy.persistance.cenum.ConditionEnum;
import com.easy.persistance.sql.Field;

public class NullCondition implements ICondition {

	protected Field field;
	protected String expression;
	public NullCondition(Field field, String ex) {
		this.field = field;
		expression = ex;
	}

	@Override
	public String getConditionSql(boolean isShowTableAlias) {
		StringBuffer where = new StringBuffer();
		where.append(field.getFieldSql(isShowTableAlias));
		where.append(" ").append(expression).append(" ").append(ConditionEnum.NULL.getValue());
		return where.toString();
	}

	@Override
	public List<Object> getValues() {
		return null;
	}
}
