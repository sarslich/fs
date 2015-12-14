package com.ken.module.queue;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ken.core.redis.Redis;
import com.ken.module.order.OrderService;

public class OrderTask implements Runnable{

	private static Logger logger =  LoggerFactory.getLogger(OrderTask.class);

	private OrderService orderService;
	
	public OrderTask(OrderService orderService){
		this.orderService = orderService;
	}
	
	
	public void run() {
		while (true) {
			try {				
				List<String> orderId = Redis.use(QueueDicKey.CACHE_NAME).blpop(QueueDicKey.ORDER_LIST);
				logger.info("get single order id "+orderId+" from redis "+QueueDicKey.ORDER_LIST);
				if (!orderId.isEmpty()) {
					orderService.doOrder(orderId.get(1));
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("线程处理队列中订单异常", e);
			}
		}
	}
	
}
