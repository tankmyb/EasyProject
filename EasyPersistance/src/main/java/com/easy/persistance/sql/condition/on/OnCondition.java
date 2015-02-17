package com.easy.persistance.sql.condition.on;

import com.easy.persistance.sql.condition.ICondition;



public class OnCondition implements IOn{

	private ICondition logic ;
	public OnCondition(ICondition logic ){
		this.logic = logic;
	}
	public String getOnSql() {
		StringBuffer where=new StringBuffer();
		where.append(" on ");
		where.append(logic.getConditionSql(true));
		return where.toString();
	}
	
}
