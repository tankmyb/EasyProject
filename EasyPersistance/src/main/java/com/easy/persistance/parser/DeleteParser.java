package com.easy.persistance.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.easy.kit.utils.string.StringSplitUtil;

public class DeleteParser implements IParser<List<Object>>{
    private String tableName;
	private Map<String,Object> params;
    private StringBuffer sql;
    private List<Object> values;
    private List<String> fields;
	public DeleteParser(String tableName,Map<String,Object> params) {
		this.tableName = tableName;
		this.params = params;
		parse();
	}

	private void parse() {
		if(params==null || params.size()==0){
			throw new RuntimeException("至少要有一个条件");
		}
		values = new ArrayList<Object>();
		fields = new ArrayList<String>();
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> m = it.next();
			fields.add(m.getKey()+"=?");
			values.add(m.getValue());
		}
	}

	@Override
	public String getSQL() {
		if (sql == null) {
			sql = new StringBuffer("delete from ").append(tableName);
			StringSplitUtil ssuField = new StringSplitUtil(" and ");
			for (String field : fields) {
				ssuField.append(field);
			}
			sql.append(" where ").append(ssuField.toString()).append(";");
		}
		return sql.toString();
	}
	

	@Override
	public List<Object> getValues() {
		return values;
	}
	

	

}
