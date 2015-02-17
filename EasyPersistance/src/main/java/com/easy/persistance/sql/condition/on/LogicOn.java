package com.easy.persistance.sql.condition.on;

import com.easy.persistance.sql.Field;
import com.easy.persistance.sql.condition.ICondition;

public class LogicOn extends On{
	private ICondition logic;
	public LogicOn(Field leftField,Field rightField,ICondition logic,String joinType){
		this.leftField = leftField;
		this.rightField = rightField;
		this.logic = logic;
		this.joinType = joinType;
	}
	@Override
	public String getOnSql(){
		StringBuffer sb = new StringBuffer(leftField.getTable().getTableSql(true));
		sb.append(" ").append(joinType).append(" ").append(rightField.getTable().getTableSql(true));
		sb.append(" on ").append(logic.getConditionSql(true));
		return sb.toString();
	}
}
