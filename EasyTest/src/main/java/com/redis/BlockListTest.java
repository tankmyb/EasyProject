package com.redis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class BlockListTest {

	public static void main(String[] args) {
		Jedis jedis = JedisPoolUtils.getJedis();
		while(true){
			try{
				List<String > list = jedis.blpop(0,"bl");
				System.out.println(list);
				for(String s:list){
					System.out.println(s);
					
				}
				System.out.println("==========");
			}catch(JedisConnectionException e){
				System.out.println("=====JedisConnectionException=======");
				e.printStackTrace();
				try {
					Thread.sleep(20000L);
				} catch (InterruptedException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}catch(Exception e){
				System.out.println("=====Exception=======");
				try {
					Thread.sleep(20000L);
				} catch (InterruptedException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
			
		}
		
	}
}
