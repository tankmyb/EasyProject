package com.netty.protobuf.mulit;

import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.MessageLite;
import com.netty.protobuf.CarInfos.Car.CarInfo;

/** 
 * 协议ID映射管理 
 * 自动生成文件，切勿修改 
 */  
public class MessageMappingManager {  
  
    /** msgId <-> MessageLite Req请求映射 */  
    private static Map<Integer, MessageLite> idClazzMap;  
  
    /** MessageLiteClass <--> msgId Resp响应映射 */  
    private static Map<Class<? extends MessageLite>, Integer> clazzIdMap;  
      static{
    	  idClazzMap = new HashMap<Integer, MessageLite>();  
          clazzIdMap = new HashMap<Class<? extends MessageLite>, Integer>();  
    
          idClazzMap.put(1, CarInfo.getDefaultInstance());  
          clazzIdMap.put(CarInfo.class, 1); 
          System.out.println(CarInfo.class);
      }
    public void init() {  
        
        //idClazzMap.put(4, EnterSceneResp.getDefaultInstance());  
        //clazzIdMap.put(EnterSceneResp.class, 4);  
}  
public static MessageLite getMessage(int messageId) {  
        return idClazzMap.get(messageId);  
    }  
  
    public static int getMessageId(Class<?> clazz) {  
        return clazzIdMap.get(clazz);  
    }  
}  