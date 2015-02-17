package com.jackjson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class ResolveUnit
{
	private static ObjectMapper mapper = new ObjectMapper();
	static{
		//创建只输出非空属性到Json字符串的Binder.  
		mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
		 mapper.getDeserializationConfig()
         .set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         mapper.getSerializationConfig().setDateFormat(df);
         mapper.getDeserializationConfig().setDateFormat(df);
	}
    /**
     * 将对象转换成Json格式的字符串
     * @param map
     * @throws Exception
     */
	public static String getJsonStr(Object obj){
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
    public static <T> T resolve(final String message,Class<T> cls){
        T object = null ;
        try{
            object = mapper.readValue(message, cls);
        }catch (JsonParseException e){
            e.printStackTrace();
        }catch (JsonMappingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return object;
    }
    public static String getNodeValue(String message,String nodeKey){
			try {
				return mapper.readTree(message).get(nodeKey).getTextValue();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}// 这里的JsonNode和XML里面的Node很像  
    	return null;
    }
    public static List<JsonNode> getNode(String message,String tagPath){
    	List<JsonNode> value = new ArrayList<JsonNode>();  
			try {
				String[] path = tagPath.split(":");   
		    JsonNode node = mapper.readTree(message);   
		    getJsonValue(node, path, value, 1);   
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}// 这里的JsonNode和XML里面的Node很像  
    	return value;
    }
    public static void getJsonValue(JsonNode node, String[] path, List<JsonNode> values, int nextIndex) {   
      if (isEmpty(node)) {   
          return;   
      }   
      // 是路径的最后就直接取值   
      if (nextIndex == path.length) {   
          if (node.isArray()) {   
              for (int i = 0; i < node.size(); i++) {   
                  JsonNode child = node.get(i).get(path[nextIndex - 1]);   
                  if (isEmpty(child)) {   
                      continue;   
                  }   
                  values.add(child);   
              }   
          } else {   
              JsonNode child = node.get(path[nextIndex - 1]);   
              if (!isEmpty(child)) {   
                  values.add(child);   
              }   
          }   
          return;   
      }   
      // 判断是Node下是集合还是一个节点   
      node = node.get(path[nextIndex - 1]);   
      if (node.isArray()) {   
          for (int i = 0; i < node.size(); i++) {   
              getJsonValue(node.get(i), path, values, nextIndex + 1);   
          }   
      } else {   
          getJsonValue(node, path, values, nextIndex + 1);   
      }   
  } 
    public static boolean isEmpty(Object obj) {   
      boolean result = true;   
      if (obj == null) {   
          return true;   
      }   
      if (obj instanceof String) {   
          result = (obj.toString().trim().length() == 0) || obj.toString().trim().equals("null");   
      } else if (obj instanceof Collection) {   
          result = ((Collection) obj).size() == 0;   
      } else {   
          result = ((obj == null) || (obj.toString().trim().length() < 1)) ? true : false;   
      }   
      return result;   
  } 
}
