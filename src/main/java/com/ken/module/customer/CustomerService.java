package com.ken.module.customer;

import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ken.core.repository.support.ibatis.IBatisModelSetup;
import com.ken.module.customer.entity.Customer;
import com.ken.module.customer.repository.CustomerRepository;

@Service
public class CustomerService {
	private static Logger log = LoggerFactory.getLogger(CustomerService.class);
	
	@Resource
	private CustomerRepository dao;
	
	/**
	 * 根据appkey获取缓存中客户
	 * @param key
	 * @return
	 */
	public Customer cacheGet(String appKey){
//		Customer ci = RedisKit.get(ThreadParamInit.cacheStart_customer + appKey);
//		if(ci==null){
//			ci = Customer.dao.findFirst(getSql("platform.customer.findByKey"), appKey);
//			if(ci!=null){
//				RedisKit.put(ThreadParamInit.cacheStart_customer + ci.getStr("appKey"),ci);
//				RedisKit.put(ThreadParamInit.cacheStart_customer+ci.getPKValue(), ci);
//			}
//		}
		IBatisModelSetup  modelSetup = new IBatisModelSetup();
		modelSetup.addParameter("appKey", appKey);
		modelSetup.setSqlName("findValidataCustomerByAppkey");		
		Customer customer = dao.findUnique(modelSetup);
	 
		return customer;
	}
	
	public void updateAmount(String customerId, Double amount){
		// todo:CACHE
		HashMap<String,Object>  modelSetup = new HashMap<String,Object> ();
		modelSetup.put("customerId", customerId);
		modelSetup.put("amount", amount);
		 
		
		dao.update("updateAmount", modelSetup);
		
	}
}
