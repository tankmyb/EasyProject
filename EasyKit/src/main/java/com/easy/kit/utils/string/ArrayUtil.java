package com.easy.kit.utils.string;

import java.util.Collection;

import org.apache.commons.lang.ArrayUtils;


public class ArrayUtil {

	public static String array2String(Object[] array){
		return array2String(array,",");
	}
	public static String array2String(Object[] array,String split){
		StringSplitUtil ssu = new StringSplitUtil(split);
		for(int i = 0;i<array.length;i++){
			ssu.append(array[i]);
		}
		return ssu.toString();
	}
	public static String collection2String(Collection collection){
		Object[] array = collection.toArray();
		return array2String(array);
	}
	public static String collection2String(Collection collection,String split){
		Object[] array = collection.toArray();
		return array2String(array,split);
	}
	public static boolean contains(Object[] array, Object objectToFind) {
		return ArrayUtils.contains(array, objectToFind);
	}
	public static int indexOf(Object[] array,String obj){
		return ArrayUtils.indexOf(array, obj, 0);
	}
}
