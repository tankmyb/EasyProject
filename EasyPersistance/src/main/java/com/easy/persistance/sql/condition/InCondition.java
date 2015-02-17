package com.easy.persistance.sql.condition;

import java.util.ArrayList;
import java.util.List;

import com.easy.kit.utils.string.StringSplitUtil;
import com.easy.persistance.sql.Field;
/**
 * 
 * 组装in条件
 *
 */
public class InCondition implements ICondition {

	private Field field;
	private List<Object> values = new ArrayList<Object>();
	public InCondition(Field field,Object[] valueArray) {
		this.field = field;
		for(Object value:valueArray){
			values.add(value);
		}
	}
	@Override
	public String getConditionSql(boolean isShowTableAlias) {
		StringBuffer where = new StringBuffer();
		where.append(field.getFieldSql(isShowTableAlias));
		where.append(" in(");
		StringSplitUtil ssu = new StringSplitUtil();
		int size = values.size();
		for(int i=0;i<size;i++){
			ssu.append("?");
		}
		where.append(ssu.toString()).append(")");
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
