package com.easy.persistance.cenum;

public enum ConditionEnum {

	IN("in"),
	IS("is"),
	ISNOT("is not"),
	NULL("null"),
	LESS("<"),
	LESSEQAL("<="),
	MORE(">"),
	MOREEQUAL(">="),
	EQUAL("="),
	NOTEQUAL("<>"),
	LIKE("like"),
	NOTLIKE("not like"),
	BW("between");
	
   
	private String value;  
	ConditionEnum(String value){
		this.value= value;
	}
	public String getValue() {
		return value;
	}
}
