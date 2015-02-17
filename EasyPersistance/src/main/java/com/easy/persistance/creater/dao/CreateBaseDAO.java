package com.easy.persistance.creater.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.easy.kit.utils.string.StringUtil;
import com.easy.persistance.creater.ACreate;
import com.easy.persistance.creater.dao.bean.BaseDAOBean;
import com.easy.persistance.util.ColumnFormatUtil;
import com.easy.persistance.util.FreemarkerUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class CreateBaseDAO extends ACreate {
	private static Configuration cfg = null;

	public CreateBaseDAO(Connection conn,String parentPath, String[] tableName, String packageName) {
		super(conn, parentPath, tableName, packageName);
	}

	public void create() {
		String className = null;
		String javaName = null;
		String javaFileName = null;
		String rowName = null;
		DatabaseMetaData metadata = null;
		try {
			Template template = FreemarkerUtil.getTemplate("/com/easy/persistance/creater/dao/template/BaseDAO.java.ftl");
			metadata = conn.getMetaData();
			for (int i = 0; i < tableName.length; i++) {
				javaName = ColumnFormatUtil.upperCaseName(tableName[i], true);
				className = javaName + "BaseDAO";
				rowName = javaName + "Row";
				javaFileName = getJavaFileName(className);
				File output = new File(javaFileName);
				Writer writer = new FileWriter(output);
				BaseDAOBean bean = new BaseDAOBean();
				bean.setPackageName(packageName);
				bean.setClassName(className);
				bean.setRowName(rowName);
				bean.setSelectName(javaName + "Select");
				String primaryKeysColumnName = getPrimaryKeysColumnName(tableName[i], metadata);
				bean.setPrimaryKeyName(primaryKeysColumnName);
				bean.setTableName(tableName[i]);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("model", bean);
				template.process(data, writer);
				writer.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(className + "表的BaseDAO生成出错", e);
		}
	}

	public static Configuration getConfiguration(String templateDir)
			throws IOException {
		if (null == cfg) {
			cfg = new Configuration();
			File templateDirFile = new File(templateDir);
			cfg.setDirectoryForTemplateLoading(templateDirFile);
			cfg.setLocale(Locale.CHINA);
			cfg.setDefaultEncoding("UTF-8");
		}
		return cfg;
	}

}
