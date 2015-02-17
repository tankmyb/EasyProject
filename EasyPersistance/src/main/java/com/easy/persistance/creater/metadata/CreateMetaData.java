package com.easy.persistance.creater.metadata;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easy.persistance.creater.ACreate;
import com.easy.persistance.creater.dao.CreateDAOImpl;
import com.easy.persistance.creater.metadata.bean.MetaDataFieldBean;
import com.easy.persistance.creater.metadata.bean.MetaDataBean;
import com.easy.persistance.util.ColumnFormatUtil;
import com.easy.persistance.util.FreemarkerUtil;
import com.easy.persistance.util.JdbcUtil;

import freemarker.template.Template;

public class CreateMetaData extends ACreate {

	public CreateMetaData(Connection conn, String parentPath,
			String[] tableName, String packageName) {
		super(conn, parentPath, tableName, packageName);
	}

	public void create() {
		DatabaseMetaData metadata = null;
		ResultSet resultSet = null;
		String fileName = null;
		String javaName = null;
		String javaFileName = null;
		try {
			Template template = FreemarkerUtil.getTemplate("/com/easy/persistance/creater/metadata/template/MetaData.java.ftl");
			metadata = conn.getMetaData();
			for (int i = 0; i < tableName.length; i++) {
				//List<String> remarkList = getRemarkList(conn,metadata,tableName[i]);
				resultSet = metadata.getColumns(null, null, tableName[i], null);
				javaName = ColumnFormatUtil.upperCaseName(tableName[i], true);
				fileName = javaName + "MetaData";
				javaFileName = getJavaFileName(fileName);
				File output = new File(javaFileName);
				Writer writer = new FileWriter(output);
				MetaDataBean bean = new MetaDataBean();
				bean.setClassName(fileName);
				bean.setPackageName(packageName);
                List<MetaDataFieldBean> fieldList = new ArrayList<MetaDataFieldBean>();				
				ResultSet rs = conn.createStatement().executeQuery(
						"select * from " + tableName[i]);
				ResultSetMetaData rsmd = rs.getMetaData();
				for (int j = 1; j <= rsmd.getColumnCount(); j++) {// 字段总数
					String colName = rsmd.getColumnName(j);
					MetaDataFieldBean fieldBean = new MetaDataFieldBean();
					fieldBean.setJavaFieldName(ColumnFormatUtil.upperCaseName(
							colName, false));
					fieldBean.setTableFieldName(colName);
					fieldList.add(fieldBean);
				}
				bean.setFieldList(fieldList);
				boolean isAutoIncrement = isAutoIncrement(conn, tableName[i]);
				if(isAutoIncrement){
					bean.setIsAutoIncrement("true");
				}else {
					bean.setIsAutoIncrement("false");
				}
				String primaryKeysColumnName = getPrimaryKeysColumnName(tableName[i], metadata);
                bean.setPk(ColumnFormatUtil.upperCaseName(primaryKeysColumnName,false));
                bean.setPrimaryKeys(primaryKeysColumnName);
                bean.setTableName(tableName[i]);
                bean.setJavaTableName(javaName);
                bean.setTableAlias(ColumnFormatUtil.getTableAlias(tableName[i]));
                Map<String, Object> data = new HashMap<String, Object>();
				data.put("model", bean);
				template.process(data, writer);
				writer.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(fileName + "表的MetaData生成出错", e);
		} finally {
			JdbcUtil.close(resultSet);
		}
	}

	
}
