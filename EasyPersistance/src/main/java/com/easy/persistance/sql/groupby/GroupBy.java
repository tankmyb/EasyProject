package com.easy.persistance.sql.groupby;

import com.easy.persistance.sql.Field;


public class GroupBy implements IGroupBy{

	private Field field;

	
	public GroupBy(Field field){
		this.field = field;
	}
	@Override
	public String getGroupBySql(boolean isShowTableAlias) {
		return field.getFieldSql(isShowTableAlias);
	}
	
}
