package com.easy.persistance.creater.select;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easy.persistance.creater.ACreate;
import com.easy.persistance.creater.select.bean.SelectFieldBean;
import com.easy.persistance.creater.select.bean.SelectBean;
import com.easy.persistance.util.ColumnFormatUtil;
import com.easy.persistance.util.DataTypeUtil;
import com.easy.persistance.util.FreemarkerUtil;
import com.easy.persistance.util.JdbcUtil;

import freemarker.template.Template;

public class CreateSelect extends ACreate {

	public CreateSelect(Connection conn, String parentPath, String[] tableName, String packageName) {
		super(conn, parentPath, tableName, packageName);
	}

	public void create(){
		DatabaseMetaData metadata = null;
		ResultSet resultSet = null;
		String fileName = null;
		try {
			Template template = FreemarkerUtil.getTemplate("/com/easy/persistance/creater/select/template/Select.java.ftl");
			metadata = conn.getMetaData();
			for (int i = 0; i < tableName.length; i++) {
				resultSet = metadata.getColumns(null, null, tableName[i], null);
				String javaName = ColumnFormatUtil.upperCaseName(tableName[i].toLowerCase(),true);
				fileName = javaName + "Select";
				String javaFileName = getJavaFileName(fileName);
				SelectBean bean = new SelectBean();
				bean.setClassName(fileName);
				bean.setPackageName(packageName);
				bean.setMetadataName(javaName+"MetaData");
				List<SelectFieldBean> fieldList = new ArrayList<SelectFieldBean>();
				while (resultSet.next()) {
					String javaColName =  ColumnFormatUtil.upperCaseName(resultSet.getString("COLUMN_NAME").toLowerCase(),false);
					String colType = DataTypeUtil.getJavaDataType(resultSet.getString("TYPE_NAME"));
					SelectFieldBean fieldBean = new SelectFieldBean();
					fieldBean.setJavaFieldName(javaColName);
					fieldBean.setFieldType(colType);
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
			throw new RuntimeException(fileName + "表的Select生成出错",e);
		}finally{
			JdbcUtil.close(resultSet);
		}
	}

}
