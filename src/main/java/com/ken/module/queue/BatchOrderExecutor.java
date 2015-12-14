package com.ken.module.queue;

import org.apache.log4j.Logger;

import com.ken.core.redis.Redis;
 

public class BatchOrderExecutor {

	private static Logger logger = Logger.getLogger(BatchOrderExecutor.class);
		
	public static void setOrder(String orderId){
		Redis.use(QueueDicKey.CACHE_NAME).rpush(QueueDicKey.BATCH_ORDER_LIST,orderId);
		logger.info("set batch order id "+orderId+" to redis ");
	}
	
}
