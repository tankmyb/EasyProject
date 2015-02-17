package com.easy.persistance.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerUtil {

	public static Template getTemplate(String templateFileName) throws IOException {
		URL url = FreemarkerUtil.class.getResource(templateFileName);
		String path = url.getPath();
		String templateFileDir = path.substring(0, path.lastIndexOf("/"));
		String templateFile = path.substring(path.lastIndexOf("/") + 1, path
				.length());
		Configuration cfg = new Configuration();
		File templateDirFile = new File(templateFileDir);
		cfg.setDirectoryForTemplateLoading(templateDirFile);
		cfg.setLocale(Locale.CHINA);
		cfg.setDefaultEncoding("UTF-8");
		Template template = cfg.getTemplate(templateFile);
		return template;
	}
}
