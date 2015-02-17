package com.easy.persistance.creater.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.easy.kit.utils.string.StringUtil;
import com.easy.persistance.creater.ACreate;
import com.easy.persistance.creater.dao.bean.DAOImplBean;
import com.easy.persistance.util.ColumnFormatUtil;
import com.easy.persistance.util.FreemarkerUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class CreateDAOImpl extends ACreate {
	
    private String ormPackageName;
	public CreateDAOImpl(String parentPath, String[] tableName, String packageName,String ormPackageName) {
		this.parentPath = parentPath;
		this.tableName = tableName;
		this.packageName = packageName;
		this.ormPackageName = ormPackageName;
		packagePath = StringUtil.replaceAll(packageName, ".", "/", false);
	}

	public void create() {
		String className = null;
		String javaName = null;
		String javaFileName = null;

		try {
			Template template = FreemarkerUtil.getTemplate("/com/easy/persistance/creater/dao/template/DAOImpl.java.ftl");
			for (int i = 0; i < tableName.length; i++) {
				javaName = ColumnFormatUtil.upperCaseName(tableName[i], true);
				className = javaName + "DAOImpl";
				javaFileName = getJavaFileName(className);
				File output = new File(javaFileName);
				Writer writer = new FileWriter(output);
				DAOImplBean bean = new DAOImplBean();
				bean.setPackageName(packageName);
				bean.setClassName(className);
				bean.setBaseDAOName(javaName+"BaseDAO");
				bean.setDaoName(javaName+"DAO");
				bean.setOrmPackageName(ormPackageName);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("model", bean);
				template.process(data, writer);
				writer.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(className + "表的DAOImpl生成出错", e);
		}
	}

	

}
