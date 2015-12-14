package com.ken.module.queue;

public interface QueueDicKey {

	/**
	 * redis 消息队列ID
	 */
	public static final String ORDER_LIST = "orders";
	
	/**
	 * 接口批量订购队列ID
	 */
	public static final String BATCH_ORDER_LIST="batch_orders";
	
	
	/**
	 * redis cache name
	 */
	public static final String CACHE_NAME = "orderCache";
	
	/**
	 * redis 消息队列存储空间
	 */
	public static final String redis_order_name = "redisOrderPlugin";
	
	/**
	 * 回调客户队列ID
	 */
	public static final String INVOKE_DATA_LIST="invoke_datas";
	
	/**
	 * redis 系统参数存储空间
	 */
	public static final String redis_sys_name = "redisSysPlugin";
	
 
	
	
}
