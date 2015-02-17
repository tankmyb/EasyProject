package com.easy.persistance.sql.condition.on;
import com.easy.persistance.sql.Field;

public class On implements IOn{
	protected Field leftField;
	protected Field rightField;
	protected String joinType;
	public On(){
	}

	public On(Field leftField,Field rightField,String joinType){
		this.leftField = leftField;
		this.rightField = rightField;
		this.joinType = joinType;
	}

	@Override
	public String getOnSql(){
		StringBuffer where=new StringBuffer();
		where.append(leftField.getTable().getTableSql(true));
		where.append(" ").append(joinType).append(" ").append(rightField.getTable().getTableSql(true));
		where.append(" on ").append(leftField.getFieldSql());
		where.append(" = ").append(rightField.getFieldSql());
		return where.toString();
	}

}
