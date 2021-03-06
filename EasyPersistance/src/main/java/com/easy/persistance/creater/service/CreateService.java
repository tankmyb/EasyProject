package com.easy.persistance.creater.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.easy.persistance.creater.ACreate;
import com.easy.persistance.creater.service.bean.ServiceBean;
import com.easy.persistance.util.ColumnFormatUtil;
import com.easy.persistance.util.FreemarkerUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class CreateService extends ACreate {
	private static Configuration cfg = null;
	public CreateService(Connection conn,String parentPath, String[] tableName, String packageName) {
		super(conn, parentPath, tableName, packageName);
	}

	public void create() {
		String className = null;
		String javaName = null;
		String javaFileName = null;
		try {
			Template template = FreemarkerUtil.getTemplate("/com/easy/persistance/creater/service/template/Service.java.ftl");
			for (int i = 0; i < tableName.length; i++) {
				javaName = ColumnFormatUtil.upperCaseName(tableName[i], true);
				className = javaName + "Service";
				javaFileName = getJavaFileName(className);
				File output = new File(javaFileName);
				Writer writer = new FileWriter(output);
				ServiceBean bean = new ServiceBean();
				bean.setPackageName(packageName);
				bean.setClassName(className);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("model", bean);
				template.process(data, writer);
				writer.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(className + "表的Service生成出错", e);
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
