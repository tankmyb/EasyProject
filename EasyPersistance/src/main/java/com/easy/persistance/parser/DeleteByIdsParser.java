package com.easy.persistance.parser;

import java.util.ArrayList;
import java.util.List;

import com.easy.kit.utils.string.StringSplitUtil;
import com.easy.kit.utils.string.StringUtil;

/**
 * 
 * 组装删除SQL,根据ID删除，如果多个ID，以,隔开
 *
 */
public class DeleteByIdsParser implements IParser<List<Object>>{
	private List<Object> values;
	private StringBuffer sql;
	private String tableName;
	private String primaryKeys;
	public DeleteByIdsParser(String tableName,String primaryKeys,String ids){
		this.tableName = tableName;
		this.primaryKeys = primaryKeys;
		parse(ids);
	}
	private void parse(String ids) {
		Integer[] array = StringUtil.toIntArray(ids);
		values = new ArrayList<Object>();
		for(int a:array){
			values.add(a);
		}
	}
	@Override
	public String getSQL() {
		if (sql == null) {
			sql = new StringBuffer("delete from ").append(tableName);
			int size = values.size();
			String expression = " = ";
			if(size>1){
				expression = " in ";
			}
			StringSplitUtil ssuField = new StringSplitUtil();
			for (int i=0;i<size;i++) {
				ssuField.append("?");
			}
			sql.append(" where ").append(primaryKeys).append(expression).append("( ").append(ssuField.toString()).append(" );");
		}
		return sql.toString();
	}
	@Override
	public List<Object> getValues() {
		return values;
	}
}
