package com.easy.persistance.creater.row;

import java.util.Iterator;
import java.util.LinkedHashSet;

import com.easy.kit.utils.Tools;
import com.easy.persistance.util.ColumnFormatUtil;

public class RowCreater {

	private String packageName;
	private LinkedHashSet<String> importSet;
	private LinkedHashSet<String> methodSet;
	private String className;
	private StringBuffer content;
	private String implementClassName;
	private String extentClassName;

	public RowCreater(String clsname, String packagename) {
		className = clsname;
		packageName = packagename;
		importSet = new LinkedHashSet<String>();
		methodSet = new LinkedHashSet<String>();
	}

	public void addMethod(String colType, String colName,String remark) {
		StringBuffer sb = new StringBuffer();
		// getter
		sb.append("  /**\r\n");
		sb.append("  * 获取【").append(remark).append("】的值\r\n");
		sb.append("  **/\r\n");
		sb.append("  public ").append(colType).append(" ");
		sb.append("get").append(ColumnFormatUtil.upperCaseName(colName, true)).append("(){\r\n");
		sb.append("    return (").append(colType).append(")getValue(\"").append(colName).append("\");\r\n");
		sb.append("  ").append("}\r\n");
		addMethodSet(sb.toString());
		// setter
		String name = ColumnFormatUtil.upperCaseName(colName, false);
		sb = new StringBuffer();
		sb.append("  /**\r\n");
		sb.append("  * 设置【").append(remark).append("】的值\r\n");
		sb.append("  **/\r\n");
		sb.append("  public void").append(" ").append("set");
		sb.append(ColumnFormatUtil.upperCaseName(colName, true));
		sb.append("(").append(colType).append(" ").append(name).append("){\r\n");
		sb.append("    setValue(\"").append(colName).append("\",").append(name).append(");\r\n");
		sb.append("  ").append("}\r\n");
		addMethodSet(sb.toString());
		sb = null;
	}

	public void addImportSet(String className) {
			importSet.add(className);
		
	}

	public StringBuffer addConstructor() {
		StringBuffer sb = new StringBuffer("  public ");
		sb.append(className).append("(){}\r\n");
		return sb;
	}

	private String addBaseMethod(String tableName, String primaryKeys) {
		StringBuffer sb = new StringBuffer("  public String getPrimaryKeyName(){\r\n");
		sb.append("    return \"").append(primaryKeys).append("\";\r\n");
		sb.append("  }\r\n");
		sb.append("  public Table getTable(){\r\n");
		sb.append("    return new Table(\"").append(tableName).append("\",\"").append(ColumnFormatUtil.getTableAlias(tableName)).append("\");\r\n");
		sb.append("  }\r\n");
		return sb.toString();
	}

	public String parse(String tableName, String primaryKeys,boolean isAutoIncrement) {
		content = new StringBuffer();
		content.append("package ").append(packageName).append(";\r\n");
		
		Iterator<String> it = importSet.iterator();
		while (it.hasNext()) {
			content.append("import ").append(it.next()).append(";\r\n");
		}
		content.append("public class ").append(className);
		if (Tools.isValid(extentClassName)) {
			content.append(" extends ").append(extentClassName);
		}
		if (Tools.isValid(implementClassName)) {
			content.append(" implements ").append(implementClassName);
		}
		content.append(" {\r\n");
		content.append("  public static final long serialVersionUID = 1L;\n");

		content.append(addConstructor());
		content.append(addBaseMethod(tableName,primaryKeys));
		it = methodSet.iterator();
		while (it.hasNext()) {
			content.append(it.next());
		}
		content.append("  public  boolean isAutoIncrement(){\r\n");
		content.append("    return "+isAutoIncrement).append(";\r\n");
		content.append("  }\r\n");
		content.append("}\r\n");
		return content.toString();
	}

	public String getImplementClassName() {
		return implementClassName;
	}

	public void setImplementClassName(String implementClassName) {
		this.implementClassName = implementClassName;
	}

	public String getExtentClassName() {
		return extentClassName;
	}

	public void setExtentClassName(String extentClassName) {
		this.extentClassName = extentClassName;
	}


	public void addMethodSet(String str) {
		methodSet.add(str);
	}
}
