package com.redis;

import redis.clients.jedis.Jedis;

public class InsertBlockList {

	public static void main(String[] args) {
		Jedis jedis = JedisPoolUtils.getJedis();
		for(int i=0;i<100;i++){
			jedis.rpush("bl", i+"");
		}
		JedisPoolUtils.returnRes(jedis);
	}
}
