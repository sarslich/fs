package com.ken.module.queue;

import com.ken.core.redis.Redis;



/**
 * redis 工具类 将ehcache系统全局变量改造成redis，以达到分布式共享目的
 * @author ryan
 * @Time 2015-10-19 15:22:10 
 */
public class RedisKit {
	
	/**
	 * 将数据以键值对形式放入redis中
	 * @param key 键
	 * @param value 值
	 */
	public static void put(String key , Object value){
		Redis.use(QueueDicKey.redis_sys_name).set(key, value);
	}
	
	/**
	 * 将数据以键值对形式放入redis中
	 * @param key 键
	 * @param value 值
	 * @param seconds 过期时间(单位秒)
	 */
	public static void put(String key , Object value ,int seconds){
		Redis.use(QueueDicKey.redis_sys_name).setex(key, seconds, value);
	}
	
	/**
	 * 将数据以键值对形式放入redis中
	 * @param key 键
	 * @param value 值
	 * @param seconds 过期时间(单位秒)
	 */
	public static Long setnx(String key , String value){
		return Redis.use(QueueDicKey.redis_sys_name).setnx(key, value);
	}
	
	/**
	 * 将key值自增+1
	 * @param key
	 * @return 返回自增后数目
	 */
	public static Long incrByOne(String key){
		return Redis.use(QueueDicKey.redis_sys_name).incrBy(key,1);
	}
	
	/**
	 * 将key的数据设置失效时间10分钟+1秒
	 * @param key 键
	 */
	public static Long expireTenMin(String key){
		return Redis.use(QueueDicKey.redis_sys_name).expire(key, 10*60+1);
	}
	
	/**
	 * 从redis中根据key获取数据
	 * @param key 键
	 * @return 值
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String key){
		return (T)Redis.use(QueueDicKey.redis_sys_name).get(key);
	}
	
	/**
	 * 从redis中根据key获取数据
	 * @param key 键
	 * @return 值
	 */
	public static void remove(String key){
		Redis.use(QueueDicKey.redis_sys_name).del(key);
	}
	
	/**
	 * 将redis中的数据全部删除
	 */
	public static void  delAll(){
		Redis.use(QueueDicKey.redis_sys_name).flushdb();
	}
	
}
