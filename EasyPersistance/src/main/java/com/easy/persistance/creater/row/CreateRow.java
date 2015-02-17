package com.easy.persistance.creater.row;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easy.kit.utils.file.FileUtil;
import com.easy.kit.utils.log.InfoLogger;
import com.easy.persistance.creater.ACreate;
import com.easy.persistance.creater.row.bean.RowFieldBean;
import com.easy.persistance.creater.row.bean.RowBean;
import com.easy.persistance.util.ColumnFormatUtil;
import com.easy.persistance.util.DataTypeUtil;
import com.easy.persistance.util.FreemarkerUtil;
import com.easy.persistance.util.JdbcUtil;

import freemarker.template.Template;

public class CreateRow extends ACreate {

	public CreateRow(Connection conn, String parentPath, String[] tableName,
			String packageName) {
		super(conn, parentPath, tableName, packageName);
	}

	public void create() {
		DatabaseMetaData metadata = null;
		ResultSet resultSet = null;
		String fileName = null;
		try {
			Template template = FreemarkerUtil.getTemplate("/com/easy/persistance/creater/row/template/Row.java.ftl");
			metadata = conn.getMetaData();
			for (int i = 0; i < tableName.length; i++) {
				//List<String> remarkList = getRemarkList(conn,metadata,tableName[i]);
				resultSet = metadata.getColumns(null, null, tableName[i], null);
				fileName = ColumnFormatUtil.upperCaseName(tableName[i],
						true)
						+ "Row";
				String javaFileName = getJavaFileName(fileName);
				RowBean bean = new RowBean();
				bean.setClassName(fileName);
				bean.setPackageName(packageName);
				
				List<RowFieldBean> fieldList = new ArrayList<RowFieldBean>();
				ResultSet rs = conn.createStatement().executeQuery(
						"select * from " + tableName[i]);
				ResultSetMetaData rsmd = rs.getMetaData();
				for (int j = 1; j <= rsmd.getColumnCount(); j++) {// 字段总数
					String colName = rsmd.getColumnName(j);
					String colType = DataTypeUtil.getJavaDataType((String)rsmd.getColumnTypeName(j));
					RowFieldBean fieldBean = new RowFieldBean();
					fieldBean.setFieldType(colType);
					fieldBean.setTableFieldName(colName);
					fieldBean.setJavaFieldName( ColumnFormatUtil.upperCaseName(colName,
							false));
					fieldList.add(fieldBean);
				}
				bean.setFieldList(fieldList);
				File output = new File(javaFileName);
				Writer writer = new FileWriter(output);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("model", bean);
				template.process(data, writer);
				writer.close();

			}
		} catch (Exception e) {
			throw new RuntimeException(fileName + "表的javaBean生成出错", e);
		} finally {
			JdbcUtil.close(resultSet);
		}
	}
}