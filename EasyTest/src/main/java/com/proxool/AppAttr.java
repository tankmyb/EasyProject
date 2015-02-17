package com.proxool;

import java.io.Serializable;




public class AppAttr implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    private String id;
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String name; //码应用属性名称
    
    private String codeAppId;//应用功能ID
    
    private String fieldType;//字段类型
    
	
    //扩展属性，
    private String appName;//应用名称
    private String value;//属性值
    private Integer appAttrValueSize;//属性值size
    private String bindId;
    private String valueid;
	public String getValueid() {
		return valueid;
	}
	public void setValueid(String valueid) {
		this.valueid = valueid;
	}

	public String getBindId() {
		return bindId;
	}


	public void setBindId(String bindId) {
		this.bindId = bindId;
	}
    
    public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

    public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getCodeAppId()
    {
        return codeAppId;
    }

    public void setCodeAppId(String codeAppId)
    {
        this.codeAppId = codeAppId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
   
	public Integer getAppAttrValueSize() {
		return appAttrValueSize;
	}
	public void setAppAttrValueSize(Integer appAttrValueSize) {
		this.appAttrValueSize = appAttrValueSize;
	}
}
