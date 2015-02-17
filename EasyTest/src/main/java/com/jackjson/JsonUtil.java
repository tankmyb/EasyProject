package com.jackjson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class JsonUtil
{
    
    private ObjectMapper mapper;
    
    public JsonUtil(Inclusion inclusion)
    {
        mapper = new ObjectMapper();
        //设置输出包含的属性   
        mapper.getSerializationConfig().setSerializationInclusion(inclusion);
        //mapper.getSerializationConfig()
        //.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //设置输入时忽略JSON字符串中存在而Java对象实际没有的属性   
        mapper.getDeserializationConfig()
            .set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    /**  
     * 创建输出全部属性到Json字符串的Binder.  
     */
    public static JsonUtil buildNormalBinder()
    {
        return new JsonUtil(Inclusion.ALWAYS);
    }
    
    /**  
     * 创建只输出非空属性到Json字符串的Binder.  
     */
    public static JsonUtil buildNonNullBinder()
    {
        return new JsonUtil(Inclusion.NON_NULL);
    }
    
    /**  
     * 创建只输出初始值被改变的属性到Json字符串的Binder.  
     */
    public static JsonUtil buildNonDefaultBinder()
    {
        return new JsonUtil(Inclusion.NON_DEFAULT);
    }
    
    /**  
     * 如果JSON字符串为Null或"null"字符串,返回Null.  
     * 如果JSON字符串为"[]",返回空集合.  
     *   
     * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句:  
     * List<MyBean> beanList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});  
     */
    public <T> T fromJson(String jsonString, Class<T> clazz)
    {
        if (jsonString==null || jsonString.equals(""))
        {
            return null;
        }
        
        try
        {
            return mapper.readValue(jsonString, clazz);
        }
        catch (IOException e)
        {
            
            return null;
        }
    }
    
    /**  
     * 如果对象为Null,返回"null".  
     * 如果集合为空集合,返回"[]".  
     */
    public String toJson(Object object)
    {
        
        try
        {
            return mapper.writeValueAsString(object);
        }
        catch (IOException e)
        {
            
            return null;
        }
    }
    
    /**  
     * 设置转换日期类型的format pattern,如果不设置默认打印Timestamp毫秒数.  
     */
    public void setDateFormat(String pattern)
    {
        if (pattern!=null)
        {
            DateFormat df = new SimpleDateFormat(pattern);
            mapper.getSerializationConfig().setDateFormat(df);
            mapper.getDeserializationConfig().setDateFormat(df);
        }
    }
    
    /**  
     * 取出Mapper做进一步的设置或使用其他序列化API.  
     */
    public ObjectMapper getMapper()
    {
        return mapper;
    }
    
    /**
     * 把字符串型的json 转换为 具体对象
     * 
     * @param json 为json字符串
     * @param obj 为具体对象的实例
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Object Json2Object(String json ,Object obj) {
        
        try {
            obj = mapper.readValue(json, obj.getClass());
            return obj;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    
}
