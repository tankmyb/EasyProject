package com.easy.persistance.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.easy.kit.utils.string.StringSplitUtil;

public class InsertParser implements IParser<List<Object>>{
	private List<String> fields;
	private StringBuffer sql;
    private List<Object> values;
    private Map<String, Object> record;
    private String tableName;
	public InsertParser(String tableName,Map<String, Object> record) {
		this.tableName = tableName;
		this.record = record;
		parse();
	}

	private void parse() {
		Map<String, Object> params = new HashMap<String, Object>(record);
		if(params==null || params.size()==0){
			throw new RuntimeException("至少要有一个字段");
		}
		fields = new ArrayList<String>();
		values = new ArrayList<Object>();
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> m = it.next();
			fields.add(m.getKey());
			values.add(m.getValue());
		}
	}
	@Override
	public String getSQL() {
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

	@Override
	public List<Object> getValues() {
		return values;
	}
	
}
