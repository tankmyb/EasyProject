package com.easy.persistance.sql.condition;

import java.util.List;

public interface ICondition {

	public String getConditionSql(boolean isShowTableAlias);
	
	public List<Object> getValues();
}
