package com.easy.persistance.sql.condition;

import java.util.ArrayList;
import java.util.List;

import com.easy.persistance.sql.Field;
import com.easy.persistance.sql.select.ISelect;
/**
 * 
 * 组装subSelect条件
 *
 */
public class SubSelectCondition implements ICondition {

	private Field field;
	private ISelect select;
	private String expression;
	private List<Object> values = new ArrayList<Object>();
	public SubSelectCondition(Field field,String expression,ISelect select) {
		this.field = field;
		this.expression = expression;
		this.select = select;
		values.addAll(select.getValues());
	}
	@Override
	public String getConditionSql(boolean isShowTableAlias) {
		StringBuffer where = new StringBuffer();
		where.append(field.getFieldSql(isShowTableAlias));
		where.append(" ").append(expression).append(" (");
		where.append(select.getSQL());
		where.append(" )");
		return where.toString();
	}
	
	public void setField(Field fieldName) {
		this.field = fieldName;
	}
	public Field getField() {
		return field;
	}

	@Override
	public List<Object> getValues() {
		return values;
	}
	

}
