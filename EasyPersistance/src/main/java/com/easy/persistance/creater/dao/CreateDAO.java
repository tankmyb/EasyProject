package com.easy.persistance.creater.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.easy.persistance.creater.ACreate;
import com.easy.persistance.creater.dao.bean.DAOBean;
import com.easy.persistance.util.ColumnFormatUtil;
import com.easy.persistance.util.FreemarkerUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class CreateDAO extends ACreate {
	private static Configuration cfg = null;
    private String ormPackageName;
	public CreateDAO(Connection conn,String parentPath, String[] tableName, String packageName,String ormPackageName) {
		super(conn, parentPath, tableName, packageName);
		this.ormPackageName = ormPackageName;
	}

	public void create() {
		String className = null;
		String javaName = null;
		String javaFileName = null;
		String rowName = null;
		DatabaseMetaData metadata = null;
		try {
			metadata = conn.getMetaData();
			Template template = FreemarkerUtil.getTemplate("/com/easy/persistance/creater/dao/template/DAO.java.ftl");
			for (int i = 0; i < tableName.length; i++) {
				javaName = ColumnFormatUtil.upperCaseName(tableName[i], true);
				className = javaName + "DAO";
				rowName = javaName + "Row";
				javaFileName = getJavaFileName(className);
				File output = new File(javaFileName);
				Writer writer = new FileWriter(output);
				String primaryKeysColumnName = getPrimaryKeysColumnName(tableName[i], metadata);
				DAOBean bean = new DAOBean();
				bean.setPackageName(packageName);
				bean.setClassName(className);
				bean.setRowName(rowName);
				bean.setOrmPackageName(ormPackageName);
				bean.setPrimaryKeyName(primaryKeysColumnName);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("model", bean);
				template.process(data, writer);
				writer.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(className + "表的DAO生成出错", e);
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
