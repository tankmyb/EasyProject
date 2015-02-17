package com.easy.persistance.sql.condition.on;

import com.easy.persistance.sql.Field;

public class RightOn extends On{

	public RightOn(Field leftField,Field rightField,String joinType){
		super(leftField,rightField,joinType);
	}

	@Override
	public String getOnSql(){
		StringBuffer where=new StringBuffer();
		where.append(" ");
		where.append(joinType).append(" ").append(rightField.getTable().getTableSql(true));
		where.append(" on ").append(leftField.getFieldSql());
		where.append(" = ").append(rightField.getFieldSql());
		return where.toString();
	}
}
