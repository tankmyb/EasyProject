package com.easy.persistance.sql.condition;

import java.util.ArrayList;
import java.util.List;

import com.easy.persistance.sql.Field;

public class Condition implements ICondition {

	private Field field;
	private String expression;
	private List<Object> values = new ArrayList<Object>();
	
	public Condition(Field field, String ex,Object value) {
		this.field = field;
		expression = ex;
		values.add(value);
	}
	

	@Override
	public String getConditionSql(boolean isShowTableAlias) {
		StringBuffer where = new StringBuffer();
		where.append(field.getFieldSql(isShowTableAlias));
		where.append(" ").append(expression).append(" ?");
		return where.toString();
	}

	public Field getField() {
		return field;
	}
	@Override
	public List<Object> getValues() {
		return values;
	}
    

}
