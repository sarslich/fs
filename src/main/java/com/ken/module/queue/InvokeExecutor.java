package com.ken.module.queue;

import org.apache.log4j.Logger;

import com.ken.core.redis.Redis;

 

public class InvokeExecutor {

	private static Logger logger = Logger.getLogger(InvokeExecutor.class);
		
	public static void setOrder(String data){
		 Redis.use(QueueDicKey.redis_order_name).rpush(QueueDicKey.INVOKE_DATA_LIST,data);
		logger.info("set invoke data "+data+" to redis ");
	}
	
}
