package com.easy.persistance.sql.column;

public interface IColumn {
	/**
	 * 
	 * @param isShowTableAlias sql是否显示表别名
	 * @return
	 */
  public String getColumnSql(boolean isShowTableAlias);
}
