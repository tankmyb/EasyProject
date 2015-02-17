package com.easy.persistance.sql.condition;

import java.util.List;

import com.easy.persistance.cenum.ConditionEnum;
import com.easy.persistance.sql.Field;

public class FieldCondition implements ICondition {

	private Field leftField;
	private Field rightField;
	private String expression;

	public FieldCondition(Field leftField, Field rightField) {
		this(leftField, ConditionEnum.EQUAL.getValue(), rightField);
	}

	public FieldCondition(Field leftField, String expression, Field rightField) {
		this.leftField = leftField;
		this.expression = expression;
		this.rightField = rightField;
	}

	@Override
	public String getConditionSql(boolean isShowTableAlias) {
		StringBuffer sb = new StringBuffer();
		sb.append(leftField.getFieldSql());
		sb.append(expression);
		sb.append(rightField.getFieldSql());
		return sb.toString();
	}

	public Field getLeftField() {
		return leftField;
	}

	public void setLeftField(Field leftField) {
		this.leftField = leftField;
	}

	public Field getRightField() {
		return rightField;
	}

	public void setRightField(Field rightField) {
		this.rightField = rightField;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public List<Object> getValues() {
		// TODO Auto-generated method stub
		return null;
	}

}
