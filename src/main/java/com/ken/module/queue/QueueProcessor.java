package com.ken.module.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ken.core.redis.Redis;
import com.ken.module.order.BackwardInvoke;
import com.ken.module.order.OrderService;

@Component 
public class QueueProcessor {
 
	private static ExecutorService pool= null;
	
	private  int poolSize = 2;
	
	@Resource
	private OrderService orderService;
	
	@Resource
	private Redis redis;
	
	@Resource
	private BackwardInvoke invoke;
	
	public  void start(){
		System.out.println("初始化："+poolSize);
		redis.start();
		
		pool=Executors.newFixedThreadPool(poolSize) ;
		 
		if (pool!=null){
			for (int i=0;i<poolSize;i++){
				pool.submit(new OrderTask(orderService));
			} 
			for(int i=0;i<poolSize;i++){  
	            pool.submit(new InvokeWorker(invoke));
	        }
		}
	}
	
	public static void stop(){
		if (pool!=null && !pool.isShutdown()){
			pool.shutdown();
		}
	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}
	
	
	
}
