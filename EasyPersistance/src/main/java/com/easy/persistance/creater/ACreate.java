package com.easy.persistance.creater;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.easy.kit.utils.string.StringUtil;
import com.easy.persistance.common.SQLAssistant;
import com.easy.persistance.resultset.CustomResultSet;
import com.easy.persistance.resultset.IRow;
import com.easy.persistance.util.ColumnFormatUtil;
import com.easy.persistance.util.JdbcUtil;
import com.easy.persistance.util.CreateUtil;

public abstract class ACreate {

	protected Connection conn;
	protected String parentPath;
	protected String[] tableName;
	protected String packageName;
	protected String packagePath;
	public ACreate(){}
	public ACreate(Connection conn, String parentPath, String[] tableName,
			String packageName) {
		this.conn = conn;
		this.parentPath = parentPath;
		this.tableName = tableName;
		this.packageName = packageName;
		packagePath = StringUtil.replaceAll(packageName, ".", "/", false);
	}

	protected String getJavaFileName(String tablename) {
		StringBuffer path = new StringBuffer(parentPath);
		path.append(packagePath).append("/");
		path.append(tablename).append(".java");
		return path.toString();
	}

	/**
	 * 获取主键名称
	 * 
	 * @param tableName
	 * @param metadata
	 * @param crs
	 * @throws SQLException
	 */
	public String getPrimaryKeysColumnName(String tableName,
			DatabaseMetaData metadata) throws SQLException {
		String primaryKeysColumnName = null;
		ResultSet primaryKeysSet = null;
		try {
			primaryKeysSet = metadata.getPrimaryKeys(null, null, tableName);
			if (primaryKeysSet.next()) {
				primaryKeysColumnName = primaryKeysSet.getString("COLUMN_NAME");
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			JdbcUtil.close(primaryKeysSet);
		}
		return primaryKeysColumnName;
	}

	/**
	 *获取字段备注
	 * @param metadata
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public List<String> getRemarkList(Connection conn,DatabaseMetaData metadata,
			String tableName) throws SQLException {
		ResultSet columnSet = null;
		List<String> remarkList = new ArrayList<String>();
		if(SQLAssistant.isMssql2000Database()){
			CustomResultSet crs = CreateUtil.getMssql2000TableStructure(conn,tableName);
			for(Iterator<IRow> it = crs.iterator();it.hasNext();){
				remarkList.add((String)it.next().get("col_desc"));
			}
		}else if(SQLAssistant.isMssql2005Database() || SQLAssistant.isMssql2008Database()){
			CustomResultSet crs = CreateUtil.getMssql2005TableStructure(conn,tableName);
			for(Iterator<IRow> it = crs.iterator();it.hasNext();){
				remarkList.add((String)it.next().get("description"));
			}
		}else if(SQLAssistant.isOracleDatabase()){
			CustomResultSet crs = CreateUtil.getOracleTableStructure(conn,tableName);
			for(Iterator<IRow> it = crs.iterator();it.hasNext();){
				remarkList.add((String)it.next().get("COMMENTS"));
			}
		}else{
			try {
				columnSet = metadata.getColumns(null, "%", tableName, "%");
				//columnSet = metadata.getColumns(null,null, tableName, null);
				while (columnSet.next()) {
					String remark = columnSet.getString("REMARKS");//
					remarkList.add(remark);
				}
			} catch (SQLException e) {
				throw e;
			}finally{
				JdbcUtil.close(columnSet);
			}
		}
		return remarkList;
	}
	public boolean isAutoIncrement(Connection conn,String tableName) throws SQLException{
		boolean isAutoIncrement = false;
		if(SQLAssistant.isOracleDatabase()){
			isAutoIncrement = CreateUtil.getOracleIsAutoIncrement(conn, tableName);
		}else{
			ResultSet rs = null;
			try {
				rs = conn.createStatement().executeQuery(
						"select * from " + tableName);
				ResultSetMetaData rsmd = rs.getMetaData();
				for (int j = 1; j <= rsmd.getColumnCount(); j++) {// 字段总数
					isAutoIncrement = rsmd.isAutoIncrement(j);
					if (isAutoIncrement) {
						break;
					}
				}
			} catch (SQLException e) {
				throw e;
			}finally{
				JdbcUtil.close(rs);
			}
		}
		
		
		return isAutoIncrement;
	}

	public abstract void create();

}
