package com.easy.persistance.sql.condition;
import java.util.ArrayList;
import java.util.List;

import com.easy.persistance.cenum.ConditionEnum;
import com.easy.persistance.sql.Field;

public class BetweenCondition implements ICondition{
	private Field field;
	private List<Object> values = new ArrayList<Object>();
	public BetweenCondition(Field field,Object startObject,Object endObject){
		this.field=field;
		values.add(startObject);
		values.add(endObject);
	}
    @Override
	public String getConditionSql(boolean isShowTableAlias){
		StringBuffer where=new StringBuffer();
		where.append(field.getFieldSql(isShowTableAlias));
		where.append(" ").append(ConditionEnum.BW.getValue()).append(" ?");
		where.append(" and ?");
		return where.toString();
	}
	@Override
	public List<Object> getValues() {
		return values;
	}
    

}
