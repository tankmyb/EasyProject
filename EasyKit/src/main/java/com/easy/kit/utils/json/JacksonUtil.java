package com.easy.kit.utils.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.easy.kit.utils.Tools;
import com.easy.kit.utils.log.ExceptionLogger;
/**
 * 
 * <解析工具类> <将JSon报文解析成Java对象>
 * 
 */
public class JacksonUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	static {
		// 创建只输出非空属性到Json字符串的Binder.
		mapper.setSerializationInclusion(
				Inclusion.NON_NULL);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mapper.setDateFormat(df);
	}

	/**
	 * 将对象转换成Json格式的字符串
	 * @param map
	 * @throws Exception
	 * @author mayb
	 */
	public static String getJsonStr(Object obj) {
			try {
				return mapper.writeValueAsString(obj);
			} catch (JsonGenerationException e) {
				ExceptionLogger.exception(e);
			} catch (JsonMappingException e) {
				ExceptionLogger.exception(e);
			} catch (IOException e) {
				ExceptionLogger.exception(e);
			}
			return null;
	}

	/**
	 * 将字符串转换成对象
	 * @param <T>
	 * @param message
	 * @param cls
	 * @return
	 * @author mayb
	 */
	public static <T> T resolve(final String message, Class<T> cls) {
		T object = null;
		try {
			object = mapper.readValue(message, cls);
		} catch (JsonParseException e) {
			ExceptionLogger.exception(e);
		} catch (JsonMappingException e) {
			ExceptionLogger.exception(e);
		} catch (IOException e) {
			ExceptionLogger.exception(e);
		}
		return object;
	}

	/**
	 * 根据key获取到相应的值,返回字符型
	 * 
	 * @param message
	 * @param nodeKey
	 * @return
	 * @author mayb
	 */
	public static String getNodeValueToString(String message, String nodeKey) throws Exception{
		return getJsonNode(message,nodeKey).getTextValue();
	}
	/**
	 * 根据key获取到相应的值,返回整型
	 * 
	 * @param message
	 * @param nodeKey
	 * @return
	 * @author mayb
	 */
	public static int getNodeValueToInt(String message, String nodeKey) throws Exception{
		
		return getJsonNode(message,nodeKey).getIntValue();
	}
	/**
	 * 根据key获取到相应的JsonNode（只能处理简单的Bean,不能处理复合bean）
	 * ,通过JsonNode里的方法就可以获得value
	 * @param message
	 * @param nodeKey
	 * @return
	 * @author mayb
	 */
	public static JsonNode getJsonNode(String message,String nodeKey){
		try {
			return mapper.readTree(message).get(nodeKey);
		} catch (JsonProcessingException e) {
			ExceptionLogger.exception(e);
		} catch (IOException e) {
			ExceptionLogger.exception(e);
		}// 这里的JsonNode和XML里面的Node很像  
		return null;
}
	/**
	 * 用于处理复合Bean
	 * @param message
	 * @param tagPath
	 * @return
	 */
	public static JsonNode getJsonNode4Complex(String message,String tagPath){
		List<JsonNode> nodeList =  getNodeList(message,tagPath);
		if(!nodeList.isEmpty() && nodeList.size() !=0){
			return nodeList.get(0);
		}else {
			return null;
		}
	}
	/**
	 * 用于处理复合Bean
	 * 如：{"name":"111","id":10,"group":{"childList":[{"children":"children"}],
	 *     "users":[],"name":"111","child":{"children":"children88"}}}这字符串，
	 * 如果我想获取child下children的值，就可以用getNodeList(message,"group:child:children")
	 * 这样就可以获得。
	 * @param message
	 * @param tagPath
	 * @return
	 * @author mayb
	 */
	public static List<JsonNode> getNodeList(String message,String tagPath){
    	List<JsonNode> value = new ArrayList<JsonNode>();  
			try {
				String[] path = tagPath.split(":");   
		    JsonNode node = mapper.readTree(message);   
		    getJsonValue(node, path, value, 1);   
			} catch (JsonProcessingException e) {
				ExceptionLogger.exception(e);
			} catch (IOException e) {
				ExceptionLogger.exception(e);
			}// 这里的JsonNode和XML里面的Node很像  
    	return value;
    }

    private static void getJsonValue(JsonNode node, String[] path, List<JsonNode> values, int nextIndex) {   
      if (Tools.isBlank(node)) {   
          return;   
      }   
      // 是路径的最后就直接取值   
      if (nextIndex == path.length) {   
          if (node.isArray()) {   
              for (int i = 0; i < node.size(); i++) {   
                  JsonNode child = node.get(i).get(path[nextIndex - 1]);   
                  if (Tools.isBlank(child)) {   
                      continue;   
                  }   
                  values.add(child);   
              }   
          } else {   
              JsonNode child = node.get(path[nextIndex - 1]);   
              if (Tools.isValid(child)) {   
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
   
}
