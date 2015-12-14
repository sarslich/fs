package com.ken.module.queue;

 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ken.core.redis.Redis;

public class OrderExecutor {

	private static Logger logger = LoggerFactory.getLogger(OrderExecutor.class);
 	
	public static  void setOrder(String orderId){
		Redis.use(QueueDicKey.CACHE_NAME).rpush(QueueDicKey.ORDER_LIST,orderId);
		logger.info("set single order id "+orderId+" to redis ");
	}
	
}
