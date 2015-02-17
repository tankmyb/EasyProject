package com.easy.persistance.parser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.easy.kit.utils.string.StringSplitUtil;
import com.easy.persistance.exception.UpdateNoRecordException;
public class UpdateParser implements IParser<List<Object>>{
	private List<String> fields = new ArrayList<String>();
	private List<Object> values;
	private StringBuffer sql;
	private Map<String, Object> updateRecord;
	private String tableName;
	private String primaryKeyName;
	private Integer pkValue;
	public UpdateParser(String tableName,String primaryKeyName,Integer pkValue,Map<String, Object> updateRecord){
		this.tableName = tableName;
		this.primaryKeyName = primaryKeyName;
		this.pkValue = pkValue;
		this.updateRecord = updateRecord;
		parse();
	}

	private void parse(){
		Map<String, Object> params=new HashMap<String,Object>(updateRecord);
		if(params.isEmpty()){
			throw new UpdateNoRecordException("没有字段更新");
		}
		fields = new ArrayList<String>();
		values = new ArrayList<Object>();
		Iterator<Entry<String,Object>> it=params.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String,Object> m=it.next();
				fields.add(m.getKey());
				values.add(m.getValue());
		}
		values.add(pkValue);
	}
	
	@Override
	public String getSQL(){
		if(sql == null){
			StringSplitUtil ssuField=new StringSplitUtil();
			for(Iterator<String> it = fields.iterator();it.hasNext();){
				String field = it.next();
				ssuField.append(field," = ?");
			}
			sql =new StringBuffer("update ").append(tableName).append(" set ");
			sql.append(ssuField.toString());
			sql.append(" where ").append(primaryKeyName).append(" = ?");
		}
		return sql.toString();
	}

	@Override
	public List<Object> getValues() {
		return values;
	}
	
	

	
}
