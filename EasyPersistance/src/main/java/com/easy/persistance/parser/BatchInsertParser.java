package com.easy.persistance.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.easy.kit.utils.string.StringSplitUtil;
import com.easy.persistance.orm.SuperRow;

/**
 * 批量插入，只能插入相同字段的SQL<br/>
 * 如：insert into table1(id,name) values(1,'aaa');<br/>
 *    insert into table1(id,name) values(2,'bbb');<br/>
 *    这样没有问题<br/>
 * 但:insert into table1(id,name) values(1,'aaa');<br/>
 *   insert into table1(id,name,age) values(1,'aaa',12);<br/>
 *   这样就出错<br/>
 *
 */
public class BatchInsertParser  implements IParser<List<List<Object>>>{

	private List<List<Object>> valuesList = new ArrayList<List<Object>>();
	private StringBuffer sql;
	private List<String> fields;
	private String tableName;
	public BatchInsertParser(){
	}
	
	public void addBatch(SuperRow bean){
		Map<String, Object> params = new HashMap<String, Object>(bean
				.getRecord());
		if(params==null || params.size()==0){
			throw new RuntimeException("至少要有一个字段");
		}
		List<Object> values = new ArrayList<Object>();
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		if(fields == null){
			fields = new ArrayList<String>();
			while (it.hasNext()) {
				Entry<String, Object> m = it.next();
				fields.add(m.getKey());
				values.add(m.getValue());
			}
		}
		
		while (it.hasNext()) {
			Entry<String, Object> m = it.next();
			values.add(m.getValue());
		}
		valuesList.add(values);
	}
	@Override
	public String getSQL(){
		if(sql==null){
			if (sql == null) {
				StringSplitUtil ssuField = new StringSplitUtil();
				StringSplitUtil ssuValue = new StringSplitUtil();

				for (String field : fields) {
					ssuField.append(field);
					ssuValue.append("?");
				}
				sql = new StringBuffer("insert into ");
				sql.append(tableName);
				sql.append(" (");
				sql.append(ssuField);
				sql.append(") values(");
				sql.append(ssuValue.toString());
				sql.append(")");
			}
			return sql.toString();
		}
		return sql.toString();
	}

	@Override
	public List<List<Object>> getValues() {
		return valuesList;
	}
	
}
