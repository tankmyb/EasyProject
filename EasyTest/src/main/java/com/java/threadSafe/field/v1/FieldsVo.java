package com.java.threadSafe.field.v1;

public class FieldsVo {
	 private String value;  
	  
	    private static String staticValue;  
	  
	    public static String getStaticValue() {  
	        return staticValue;  
	    }  
	  
	    public static void setStaticValue(String staticValue) {  
	        FieldsVo.staticValue = staticValue;  
	    }  
	  
	    public String getValue() {  
	        return value;  
	    }  
	  
	    public void setValue(String value) {  
	        this.value = value;  
	    }  
}
