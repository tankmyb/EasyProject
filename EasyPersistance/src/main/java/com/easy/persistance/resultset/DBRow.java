package com.easy.persistance.resultset;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.easy.kit.utils.string.StringSplitUtil;
import com.easy.persistance.common.Constant;

public class DBRow implements IRow,Serializable{

	private static final long serialVersionUID=1L;
	protected Map<String,Object> values=new HashMap<String,Object>();
	
	public boolean contains(String name){
		Object value=values.get(name);
		return value!=null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public  <T> T get(String name){
		T value=(T)values.get(name);
		return value;
	}
	@Override
	public Integer getInteger(String name) {
		return (Integer)values.get(name);
	}
	@Override
	public String getString(String name) {
		return (String)values.get(name);
	}
	public void set(String name,Object value){
		values.put(name,value);
	}
	@Override
	public Map<String,Object> getMap()  {
		return values;
	}
	@Override
	public String toCsv()throws Exception{
    return toCsv((String[])values.keySet().toArray(new String[0]));
  }

  public String toCsv(String... labels) throws Exception{
  	StringBuffer sb = new StringBuffer();
  	StringSplitUtil ssu = new StringSplitUtil(",",Constant.CRLF);
  	for(int i=0;i<labels.length;i++){
  		ssu.append(labels[i]);
		}
		sb.append(ssu.toString());
		ssu = new StringSplitUtil(",",Constant.CRLF);
    for(int j=0;j<labels.length;j++){
    	ssu.append(this.get(labels[j]));
    }
    sb.append(ssu.toString());
    return sb.toString();    
  }



}
