package com.socket.nio.nioSession;

/** 
 * 配置信息 
 * @author Ritsky 
 */  
  
public class Config {  
      
    public static String GAME_SERVER_ADDRESS = "127.0.0.1";           //游戏服务器地址   
    public static int GAME_SERVER_PORT = 8899;                       //游戏服务器端口   
      
  
    public static int MOBILE_SERVER_PORT = 9999;                      //手机服务器端口   
    public static int MOBILE_NET_THREAD_NUM = 1;                      //网络线程数   
      
    public static long GAME_CLIENT_HEART_TIME = 20 * 1000;                            //心跳时间   
      
    //public static int GAME_CLIENT_WRITE_REPEAT_COUNT = 3;                   //向Game写数据不成功时，重复写数据的次数   
    //public static int GAME_CLIENT_AFRESH_NUM  = 5;              //当重启失败时，可以尝试5次以上的重启   
      
    public static int PLAYER_QUEUE_SIZE = 1000;                      //玩家队列大小   
      
      
    public static int REGISTER_NUM = 100;                   //每次注册的数目   
      
      
    public static String MOBILE_SERVER_PROTOCOL_CLASS =   "game.server.ProtocolClass";             //手机服务器协议解协类   
      
    public static String MOBILE_SERVER_LOGIC_CLASS = "game.logic.LogicProcess";                  //手机服务器逻缉实现类   
      
    public static String GAME_CLIENT_PROTOCOL_CLASS =  "game.client.ClientProtocolClass";                //游戏客户端协议解协类   
      
    public static String GAME_CLIENT_LOGIC_CLASS = "game.logic.LogicProcess";                    //游戏客户端逻缉实现类   
      
      
    public static int GAME_CLIENT_OUT_QUEUE_SIZE =  10000;               //游戏客户端输出队列大小   
      
      
    public static long CHECK_PLAYER_INTERVAL_TIME  = 10 * 60 * 1000;  
      
      
    public static int MOBILE_SERVER_IN_BUFFER_SIZE = 10240;  
    public static int MOBILE_SERVER_OUT_BUFFER_SIZE = 10240;  
      
    public static long MOBILE_SERVER_OUT_TIME = 30 * 1000;                  //超时时间   
    public static long MOBILE_SERVER_DAEMON_INTERVAL = 10 * 60 * 1000;      //守护间隔时间   
}  
