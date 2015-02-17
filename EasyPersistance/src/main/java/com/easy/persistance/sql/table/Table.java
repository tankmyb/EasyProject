package com.easy.persistance.sql.table;

import com.easy.kit.utils.Tools;


public class Table implements ITable{

	private String tableName;
	private String aliasName;


	public Table(String tn) {
		tableName = tn;
	}

	public Table(String tn, String an) {
		tableName = tn;
		aliasName = an;
	}

	public String getTableName() {
		return tableName;
	}



	public String getAliasName() {
		return aliasName;
	}

    
	public String getTableSql(boolean isShowTableAlias) {
		StringBuffer table = new StringBuffer(tableName);
		if(isShowTableAlias && Tools.isValid(aliasName)){
			table.append(" ").append(aliasName);
		}
		return table.toString();
	}

	public static void main(String[] args) {

	}
}
