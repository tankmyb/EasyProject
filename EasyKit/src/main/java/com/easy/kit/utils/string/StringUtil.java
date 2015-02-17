package com.easy.kit.utils.string;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.easy.kit.utils.Tools;

public class StringUtil{
	public static String removeBlank(String str){
		return str.replaceAll(" ","");
	}
	/**
	 * 经过测试，str="1,2,3,4,",输出长度也是4
	 * @param str
	 * @return
	 */
	public static String[] toSplitStr(String str){
		return toSplitStr(str,",");
	}

	public static String[] toSplitStr(String str,String separator){
		if(str==null)
			return null;
		str=removeBlank(str);
		return str.split(separator);
	}
	public static List<String> str2StrList(String str){
		return Arrays.asList(toSplitStr(str));
	}
	public static List<Integer> str2IntList(String str){
		return Arrays.asList(toIntArray(str));
	}
	public static Integer[] toIntArray(String str){
		String[] strArr = toSplitStr(str);
		return toIntArray(strArr);
	}
	public static Integer[] toIntArray(String[] arr){
		if(arr==null)
			return null;
		Integer[] result=new Integer[arr.length];
		for(int i=0;i<result.length;i++){
			result[i]=new Integer(arr[i]);
		}
		return result;
	}

	public static String removeLastStr(String str,String delStr){
		String lastStr=str.substring(str.length()-delStr.length(),str.length());
		if(lastStr.equals(delStr)){
			return str.substring(0,str.length()-delStr.length());
		}
		return str;
	}

	

	

	public static boolean isEqual(String o1,String o2,boolean isIgnoreCase){
		if(isIgnoreCase){
			if(o1==o2)
				return true;
			if(o1!=null)
				return o1.equalsIgnoreCase(o2);
			if(o2!=null)
				return o2.equalsIgnoreCase(o1);
			return false;
		}else
			return Tools.isEqual(o1,o2);
	}
	public static String InsertZero(String value,int outputlen){
		String result="";
		int insertlen=outputlen-value.length();
		for(int i=0;i<insertlen;i++){
			result=result.concat("0");
		}
		result=result.concat(value);
		return result;
	}

	public static Map<String,String> str2Map(String s,String separator){
		Map<String,String> result=new LinkedHashMap<String,String>();
		for(StringTokenizer st=new StringTokenizer(s,",");st.hasMoreElements();){
			String temp[]=st.nextToken().split(separator);
			if(temp.length==2){
				result.put(temp[0],temp[1]);
			}
		}
		return result;
	}
	
	public static String replace(String str,Object[] objArray){
		return replace(str, "\\?", objArray);
	}
	public static String replace(String str,String regex,Object[] objArray){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		int i =0;
		while (matcher.find()) {
		    matcher.appendReplacement(sb, String.valueOf(objArray[i++]));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static String format(String templet,String...strings){
		for(int i=0;i<strings.length;i++){
			templet=replaceAll(templet,"{"+i+"}",strings[i],false);
		}
		return templet;
	}

	/**
	 * 替换所有
	 * @param str 
	 * @param fs  被替换的字符串
	 * @param rs  替换的字符串
	 * @param isIgnoreCase 是否区分大小写
	 * @return
	 */
	public static String replaceAll(String str,String fs,String rs,boolean isIgnoreCase){
		int ipos=0;
		StringBuffer sb=new StringBuffer(str);
		if(isIgnoreCase){
			while(ipos>=0){
				ipos=instr(sb.toString(),fs,ipos);
				if(ipos>=0){
					sb=sb.replace(ipos,ipos+fs.length(),rs);
					ipos+=rs.length();
				}
			}
		}else{
			while(ipos>=0){
				ipos=sb.indexOf(fs,ipos);
				if(ipos>=0){
					sb=sb.replace(ipos,ipos+fs.length(),rs);
					ipos+=rs.length();
				}
			}
		}
		return sb.toString();
	}

	public static int instr(String s,String key,int start){
		int result=0;
		if(start<0){
			start=0;
		}
		if(s.length()<start+1){
			return -1;
		}
		s=s.substring(start,s.length()).toLowerCase();
		result=s.indexOf(key.toLowerCase());
		if(result>=0){
			result=start+result;
			return result;
		}else{
			return -1;
		}
	}
	
	public static String concat(Object... array){
  	StringBuffer result=new StringBuffer();
  	if(array!=null) {
  		for(int i=0;i<array.length;i++){
				result.append(array[i]==null?"":array[i]);
			}
  	}
  	return result.toString();
  }
	/**
	 * 将首字母小写
	 * 
	 * @param str
	 * @return String 返回首字母小写的字符串
	 */
	public static String firstLetterLowercase(String str) {
		if (Tools.isValid(str)) {
			StringBuffer wrod = new StringBuffer();
			str = wrod.append(str.substring(0, 1).toLowerCase()).append(str.substring(1)).toString();
		}
		return str;
	}
}
