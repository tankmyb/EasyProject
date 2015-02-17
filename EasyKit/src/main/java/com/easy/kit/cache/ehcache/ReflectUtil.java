package com.easy.kit.cache.ehcache;

import java.lang.reflect.Field;

public class ReflectUtil {

	/**
	 * 功能说明: 通过域名取得bean的属性值
	 * 
	 */
	public static Object getPropertyValue(Object bean,String fieldName){

		Field[] fields=bean.getClass().getDeclaredFields();
		Field.setAccessible(fields,true);
		Object obj=null;
		for(int i=0;i<fields.length;i++){
			Field field=fields[i];

			if(fieldName.equals(field.getName())){
				try{
					obj=field.get(bean);
					break;
				} catch(IllegalArgumentException e){
					e.printStackTrace();
				} catch(IllegalAccessException e){
					e.printStackTrace();
				}
			}
		}
		return obj;
	}
}
