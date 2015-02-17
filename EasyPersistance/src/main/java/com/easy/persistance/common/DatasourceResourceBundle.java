package com.easy.persistance.common;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DatasourceResourceBundle {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("datasource");
	 /**
	   * 得到属性的字符串值
	   * @param key
	   * @return String
	   */
	    public static String getString(final String key) {
	        try {
	           return RESOURCE_BUNDLE.getString(key);
	        } catch (MissingResourceException e) {
	          throw new RuntimeException("没有相应的属性",e); 
	       }
	    } 
}
