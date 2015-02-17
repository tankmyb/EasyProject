package com.socket.nio.nioSession;

import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;  
  
import org.w3c.dom.Document;  
import org.w3c.dom.Element;  
import org.w3c.dom.NodeList;  
  
public class ReadConfig {  
      
    //private static ReadConfig instance = null;     //唯一实例   
      
    private String configFile = null;               //配置文件   
      
    private Document doc = null;                    //文档   
      
    private ReadConfig() {}  
      
    /** 
     * 生产一个新的实例 
     * @param configFile  配置文件 
     * @return ReadConfig 
     */  
    public static ReadConfig newInstance(String configFile) {  
          
          
        ReadConfig instance = new ReadConfig();  
          
        instance.configFile = configFile;  
          
        try {  
            instance.init();  
        } catch (Exception e) {  
            instance = null;  
            e.printStackTrace();  
            return null;  
        }  
          
        return instance;   
    }  
      
      
  
      
      
    /** 
     * 初始化配置 
     * @throws Exception 
     */  
    private void init() throws Exception{  
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();  
        domFactory.setValidating(false);  
        domFactory.setNamespaceAware(true);  
        DocumentBuilder domBuilder = null;  
          
        domBuilder = domFactory.newDocumentBuilder();  
          
        doc = domBuilder.parse(configFile);  
    }  
      
  
    /** 
     * 获取配置文件 
     * @return String 
     */  
    public String getConfigFile() {  
        return configFile;  
    }  
      
      
    /** 
     * 获得指定的值 
     * @param name 
     * @return 
     */  
    public String getString(String name) {  
        Element rootElement = doc.getDocumentElement();  
          
        String[] names = name.split("/");  
          
        Element element = rootElement;  
          
        for(int i=0; i<names.length; i++) {  
            NodeList nodeList = element.getElementsByTagName(names[i]);  
            if(nodeList.getLength() == 0) {  
                return null;  
            }  
              
            element = (Element) nodeList.item(0);  
        }  
        return element.getTextContent();  
    }  
      
      
    public int getInt(String name) {  
        String value = getString(name);  
          
        return Integer.parseInt(value);  
    }  
      
    public long getLong(String name) {  
        String value = getString(name);  
          
        return Long.parseLong(value);  
    }  
}  
