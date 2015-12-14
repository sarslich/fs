package com.ken.module.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.ken.module.api.adapter.BackwardAdapter;

/**
 * API服务创建工厂
 * @author YangJiang
 * @time   2015年9月7日 下午12:26:43
 */

public class ApiFactory {

	private static final Map<String,Object> serviceMap = Collections.synchronizedMap(new HashMap<String,Object>());
	
	public static final ApiFactory apiFactory = new ApiFactory();

 	private BackwardAdapter backwardAdapter;
	
	//在此添加服务实例
	public ApiFactory(){
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext(); 
		backwardAdapter =   wac.getBean(BackwardAdapter.class);

		
		serviceMap.put("order",backwardAdapter);
		//serviceMap.put("order",BackwardAdapter.adapter);
		//serviceMap.put("backward.findUserInfo",BackwardAdapter.adapter);
		//serviceMap.put("backward.findOders",BackwardAdapter.adapter);
		//serviceMap.put("mobile.charge",BackwardAdapter.adapter);
		//serviceMap.put("backward.orderStatus",BackwardAdapter.adapter);
	}
	
	/**
	 * 获取接口服务实例
	 * @param params
	 * @return
	 */
	public Object getApiService(String methodName){
		return serviceMap.get(methodName);
	}

	public static Map<String, Object> getServiceMap() {
		return serviceMap;
	}
	
}
