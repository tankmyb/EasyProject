package com.easy.persistance.sql;

import com.easy.persistance.sql.table.ITable;

public class Field {
    private ITable table;
    private String fieldName;
    public Field(ITable table,String fieldName){
    	this.table = table;
    	this.fieldName = fieldName;
    }
		public ITable getTable() {
			return table;
		}
		public void setTable(ITable table) {
			this.table = table;
		}
		public String getFieldName() {
			return fieldName;
		}
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		public String getFieldSql(){
			StringBuffer sb = new StringBuffer();
			sb.append(table.getAliasName()).append(".").append(fieldName);
			return sb.toString();
		}
		public String getFieldSql(boolean isShowTableAlias){
			StringBuffer sb = new StringBuffer();
			if(isShowTableAlias){
				sb.append(getFieldSql());
			}else {
				sb.append(getFieldName());
			}
			return sb.toString();
		}
}
