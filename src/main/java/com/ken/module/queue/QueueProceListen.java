package com.ken.module.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.ken.core.redis.Redis;
 

public class QueueProceListen implements  ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private QueueProcessor processor;
//	@Autowired
//	private Redis redis;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
 
		if (event instanceof ContextRefreshedEvent) {
			processor.start();
//			redis.start();
		}		
	}
//	public QueueProcessor getProcessor() {
//		return processor;
//	}
//	public void setProcessor(QueueProcessor processor) {
//		this.processor = processor;
//	}
//	
 
 

}
